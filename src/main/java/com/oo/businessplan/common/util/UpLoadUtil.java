package com.oo.businessplan.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.oo.businessplan.common.pageModel.MethodResult;


/**
 * 
 * @author cyz
 *
 */
public class UpLoadUtil {
	
	   //public static final String LOCALPREFIX = File.separator + "usr" + File.separator + "local" + File.separator + "tomcat" + File.separator + "O.OBusinessPlanFile";
	   
	   //public static final String LOCALPREFIX = "E:" + File.separator + "O.OMusicRelated";
	
	   public static final String LOCALPREFIX = "D:" + File.separator + "gitRes" + File.separator + "O.OBusinessPlanFile";
	   
	   public final String[] models = {"admin", "employee", "novel"};
	   public final String[] types = {"img", "img", "img"};
	    
	   /**
	    * 配置设定的可用的文件格式,value中的文件格式以 , 隔开
	    */
	   private Map<String,String> suffixMap;
	
	   private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	   /**
	    * 上传文件，params是一个key是字符串，value是一个key和value都是字符串的map的map类型，其中key是唯一标识，map中可存放
	    * 以下的entry，分别是 :targetPath：上传的目标路径，type:文件类型,newName:新的名字, check: 是否开启校验
	    * @param request
	    * @param params  
	    * @return
	    */
	   public MethodResult<Map<String, String>>  uploadFile(HttpServletRequest request,Map<String,Map<String,String>> params){
		   
		   MethodResult<Map<String, String>> result = new MethodResult<>();
		   
		   Map<String, String> pathMap = new HashMap<>(5);
		   
		   //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
		   CommonsMultipartResolver resolver = 
				                  new CommonsMultipartResolver(request.getSession().getServletContext());
		   //检查form中是否有enctype="multipart/form-data"
		   if (!resolver.isMultipart(request)) {
			   return result.fail("没有可上传的文件");
		   }
		   //将request变成多部分request
		   MultipartHttpServletRequest mRequest =(MultipartHttpServletRequest)request;
		   //获取multiRequest 中所有的文件名
		   Iterator<String> iter=mRequest.getFileNames();
		   while (iter.hasNext()) {
			   //一次遍历所有文件
			   MultipartFile file =mRequest.getFile(iter.next());		
			   if (file == null) {
				   continue;
			   }
			   //获得上传文件在jsp页面中的标签的name属性值
               String tagName = file.getName();
               Map<String,String> fileconfig = params.get(tagName);
               if (fileconfig==null) {
			       continue;
			   }
               if (!StringUtil.isEmpty(fileconfig.get("size")) && (file.getSize()/1024l) > Long.parseLong(fileconfig.get("size"))) {
            	  return result.fail("文件过大");
               }
               //如果有type则判断文件数据格式是否符合要求
               String oldFileName = file.getOriginalFilename();
               String type = fileconfig.get("type");
               String suffix = checkFormatLegal(oldFileName);
               //判断是否符合要求的数据格式
               if (Boolean.valueOf(fileconfig.get("check")) && !match(suffix, type)) {
            	   continue;
               }
               //根据名字获得对应的路径
			   String path = fileconfig.get("targetPath")
			   , newPath = path
               //拼接路径 /home/soft01/OOMusicPic/path/ad,om_id.end
			   , newName = fileconfig.get("newName");
			   if (newName!=null) {
			    	//拼接字符串
				   newPath = UpLoadUtil.LOCALPREFIX + (path = path +File.separator+newName+"."+suffix);
			   }else {
					synchronized (sdf) {
						Date date = new Date();
						String dateStr = sdf.format(date);
						newPath =UpLoadUtil.LOCALPREFIX + (path = path +File.separator+dateStr+"."+suffix);
					}
				}
			    //上传文件
			    try {
					file.transferTo(new File(newPath));
					pathMap.put(tagName, path);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		   
	    	return result.success(pathMap);
	   }
	   
	   public MethodResult<String> uploadFile(HttpServletRequest request, String key, String targetPath, String newName, Long maxSize, Byte fileType) {
		   
		   MethodResult<String> result = new MethodResult<>();

		   Map<String, MultipartFile> fileMap = getFile(key, request);
		   if (fileMap == null) {
			   return result.fail("没有可上传的文件");
		   }
		   MultipartFile file = fileMap.get(key);
		   
		   if (file == null) {
			   return result.fail("不存在目标文件");
		   }
           
           
           //如果有type则判断文件数据格式是否符合要求
           String oldFileName = file.getOriginalFilename();
           String suffix = checkFormatLegal(oldFileName);    
           //根据名字获得对应的路径
		   String path = targetPath
		   , newPath = path;
		   if (newName!=null) {
		    	//拼接字符串
			   newPath = UpLoadUtil.LOCALPREFIX + (path = path +File.separator+newName+"."+suffix);
		   }else {
				synchronized (sdf) {
					Date date = new Date();
					String dateStr = sdf.format(date);
					newPath =UpLoadUtil.LOCALPREFIX + (path = path +File.separator+dateStr+"."+suffix);
				}
			}
		    //上传文件
		    try {
				file.transferTo(new File(newPath));
				return result.success("");
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		   
		   return result.fail("系统错误");
	   }
	   
	   public  String getRandomName (String sign) {

			return (sign == null ? "" : sign) + sdf.format(new Date());
	   }
	   
	   /**
	    * 持久化文件
	    * @param file
	    * @param targetPath
	    * @param fileName
	    * @return
	    */
	   public String filePersistence(MultipartFile file, String targetPath, String fileName) {
		      
		    //判断目录是否存在
		   File dir = new File(LOCALPREFIX + File.separator + targetPath);
		   if (!dir.exists() || !dir.isDirectory()) {
			   dir.mkdir();
		   }
		   System.out.println(targetPath);
		   String oldFileName = file.getOriginalFilename();
           String suffix = checkFormatLegal(oldFileName);
           
           String newPath = UpLoadUtil.LOCALPREFIX + (targetPath = targetPath +File.separator+fileName+"."+suffix);
           //上传文件
	       try {
		     file.transferTo(new File(newPath));
			 return targetPath;
		   } catch (IllegalStateException e) {
			 e.printStackTrace();
		   } catch (IOException e) {
			 e.printStackTrace();
		   }
		   return null;
		   
	   }
	   
	   /**
	    * 验证文件
	    * @param file
	    * @param maxSize
	    * @param type
	    * @return
	    */
	   public MethodResult<String> validFile(MultipartFile file, Long maxSize, String type) {
		   
		   MethodResult<String> result = new MethodResult<String>();
		   
		   long size = file.getSize()/1024l;
		   if (maxSize != null && size > maxSize) {
			   return result.fail("文件过大:" + size + "kb");
	       }
		   //如果有type则判断文件数据格式是否符合要求
           String oldFileName = file.getOriginalFilename();
           String suffix = checkFormatLegal(oldFileName);
           //判断是否符合要求的数据格式
           if (type != null && !match(suffix, type)) {
        	   return result.fail("文件不符合类型");
           }
		   return result.success();
	   }
	   
	   /**
	    * 获取上传的文件对象， 如果key为null，则获取所有的文件的map，如果传入key，则获取对应的文件
	    * @param key
	    * @param request
	    * @return
	    */
	   public Map<String, MultipartFile> getFile(String key, HttpServletRequest request) {
		   
		 //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
		   CommonsMultipartResolver resolver = 
				                  new CommonsMultipartResolver(request.getSession().getServletContext());
		   //检查form中是否有enctype="multipart/form-data"
		   if (!resolver.isMultipart(request)) {
			   return null;
		   }
		   //将request变成多部分request
		   MultipartHttpServletRequest mRequest =(MultipartHttpServletRequest)request;
		   
		   if (key == null) {
			   return mRequest.getFileMap();
		   }
		   
		   Map<String, MultipartFile> result = new HashMap<>();
		   result.put(key, mRequest.getFile(key));
		   
		   return result;
	   }
	   
	   
	   /**
	    * 判断文件名的格式是否符合xx.yy，如果是则返回yy,否则返回null
	    * @param fileName
	    * @return
	    */
	   public String checkFormatLegal(String fileName){
		   int index = fileName.lastIndexOf(".");
		   return index==-1 && index < fileName.length()?null:fileName.substring(index+1);
	   }
	   
	   /**
	    * 判断是否包含
	    * @param str
	    * @param strs
	    * @return
	    */
	   public boolean contain(String str,String[] strs){
		     for (int i = 0,len=strs.length; i < len; i++) {
					if (strs[i].equals(str)) {					
						return true;
					}
		     }
		     return false;
		   
	   }
	   
	   /**
	    * 
	    * @param target 校检对象
	    * @param src  校检库
	    * @param type 数据格式类型
	    * @param flag 是否开启数据格式校检
	    * @return
	    */
	   public boolean match(String target,String type){		
		   
		   String key = null;
		   if (type==null || StringUtil.isEmpty(type)) {
			  Set<String> set = suffixMap.keySet();
			  for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {				   
				   key = iterator.next();
				   System.out.println(key);
				   System.out.println(target);
				   if (suffixMap.get(key).indexOf(target)>-1) {
					   return true;
				   }			
			  }
			  return false;
		   }
		   
		   return suffixMap.get(type).indexOf(target)>-1;
	   }

	/**
	 * 删除文件
	 * @param path
	 * @return
	 */
	public boolean deleteFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		return file.delete();
	}

	public Map<String, String> getSuffixMap() {
		return suffixMap;
	}


	public void setSuffixMap(Map<String, String> suffixMap) {
		this.suffixMap = suffixMap;
	}
	   
	
 
}
