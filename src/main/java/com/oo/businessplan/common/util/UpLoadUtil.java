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


/**
 * 
 * @author cyz
 *
 */
public class UpLoadUtil {
	
	   //public static final String LOCALPREFIX = File.separator + "usr" + File.separator + "local" + File.separator + "tomcat" + File.separator + "OOBusinessPlanFile";
	   
	   public static final String LOCALPREFIX = "E:" + File.separator + "O.OMusicRelated";
	   
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
	   public Map<String,String>  uploadFile(HttpServletRequest request,Map<String,Map<String,String>> params){
		   
		   Map<String,String> result = new HashMap<>();
		   
		   //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
		   CommonsMultipartResolver resolver = 
				                  new CommonsMultipartResolver(request.getSession().getServletContext());
		   //检查form中是否有enctype="multipart/form-data"
		   if (!resolver.isMultipart(request)) {
			   return result;
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
               //如果有type则判断文件数据格式是否符合要求
               String oldFileName = file.getOriginalFilename();
               String type = fileconfig.get("type");
               String suffix = checkFormatLegal(oldFileName);
               //判断是否符合要求的数据格式
               boolean mat = match(suffix, type,Boolean.valueOf(fileconfig.get("check")));
               if (!mat) {
				   continue;
			   }
               //根据名字获得对应的路径
			   String path = fileconfig.get("targetPath");					  
               //拼接路径 /home/soft01/OOMusicPic/path/ad,om_id.end
			   String newName = fileconfig.get("newName");
			   if (newName!=null) {
			    	//拼接字符串
			    	path = path+File.separator+newName+"."+suffix;
			   }else {
					synchronized (sdf) {
						Date date = new Date();
						String dateStr = sdf.format(date);
						path = path+File.separator+dateStr+"."+suffix;
					}
				}
			    //上传文件
			    try {
					file.transferTo(new File(path));
					result.put(tagName, path);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		   
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
	   public boolean match(String target,String type,boolean flag){
		   
		   if (!flag) {
			  return !flag;
		   }
		   
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


	public Map<String, String> getSuffixMap() {
		return suffixMap;
	}


	public void setSuffixMap(Map<String, String> suffixMap) {
		this.suffixMap = suffixMap;
	}
	   
	
 
}
