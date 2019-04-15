package create;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.oo.businessplan.common.util.DateUtil;
import com.oo.businessplan.common.util.FieldMeta;


/**
 *
 * @author hzh
 * @version 创建时间：2018年7月19日 下午7:21:08
 */
public class BeanUtils {

	// 公共部分
	private static final String RT_1 = "\r\n";
	private static final String RT_2 = RT_1 + RT_1;
	private static final String BLANK_1 = " ";
	private static final String BLANK_4 = "    ";
	private static final String BLANK_8 = BLANK_4 + BLANK_4;
	private static final String BLANK_12 = BLANK_8 + BLANK_4;
	private static final String BLANK_16 = BLANK_12 + BLANK_4;
	// 注释部分
	private static final String ANNOTATION_AUTHOR_PARAMTER = "@author ";
	public static final String ANNOTATION_AUTHOR_NAME = "@name";
	private static final String ANNOTATION_AUTHOR = ANNOTATION_AUTHOR_PARAMTER + ANNOTATION_AUTHOR_NAME;
	private static final String ANNOTATION_DATE = "@version 创建时间：";
	private static final String ANNOTATION = "/**" + RT_1 + BLANK_1 + "*" + BLANK_1 + RT_1 + BLANK_1 + "*" + BLANK_1
			+ ANNOTATION_AUTHOR + RT_1 + BLANK_1 + "*" + BLANK_1 + ANNOTATION_DATE + getDate() + RT_1 + BLANK_1 + "*/"
			+ RT_1;

	private String authorName = "";// 作者名称

	private String tableName = "";// 表名

	private String tableDes = "";// 表信息

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDes() {
		return tableDes;
	}

	public void setTableDes(String tableDes) {
		this.tableDes = tableDes;
	}

	/**
	 * 创建bean的Mapper<br>
	 * 
	 * @param c
	 * @throws Exception
	 */
	public void createBeanMapper(Class c) throws Exception {
		String cName = c.getName();
		String daoUrl = getPackageName(cName) + ".mapper";
		String daoPath = daoUrl.replace(".", "/");
		System.out.println(cName);
		System.out.println(daoUrl);
		System.out.println(daoPath);

		String fileName = System.getProperty("user.dir") + "/src/main/java/" + daoPath + "/" + getLastChar(cName)
				+ "Mapper.java";
		File f = new File(fileName);
		if (f.exists()) { // 如果已经存在f文件了
			System.out.println("文件名：" + f.getAbsolutePath()); // 打印绝对路径
			System.out.println("文件大小：" + f.length());
			System.out.println("文件已存在，不能再创建。");
			return;
		} else {
			f.getParentFile().mkdirs();
			// getParentFile()返回File类型的父路径，mkdirs()创建所有的路径
			try {
				f.createNewFile(); // 至此真正在硬盘上创建了myTest.txt文件。
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("package ").append(daoUrl);
		buffer.append(";").append(RT_2);
		buffer.append("import com.oo.businessplan.basic.mapper.BaseMapper;").append(RT_1);
		buffer.append("import ").append(cName).append(";").append(RT_1);

		buffer.append(RT_2).append(ANNOTATION.replace(ANNOTATION_AUTHOR_NAME, authorName));
		buffer.append("public interface ").append(getLastChar(cName));
		buffer.append("Mapper extends BaseMapper<").append(getLastChar(cName));
		buffer.append("> {");
		buffer.append(RT_1).append("}");
		FileWriter fw = new FileWriter(f);
		fw.write(buffer.toString());
		fw.flush();
		fw.close();
		showInfo(fileName);
	}

	/**
	 * 创建bean的ServiceImpl<br>
	 * 
	 * @param c
	 * @throws Exception
	 */
	public void createBeanServiceImpl(Class c) throws Exception {
		String cName = c.getName();
		String beanName = getLastChar(cName);
		String daoUrl = getPackageName(cName) + ".service.impl";
		String daoPath = daoUrl.replace(".", "/");
		String fileName = System.getProperty("user.dir") + "/src/main/java/" + daoPath + "/" + getLastChar(cName)
				+ "ServiceImpl.java";
		File f = new File(fileName);
		if (f.exists()) { // 如果已经存在f文件了
			System.out.println("文件名：" + f.getAbsolutePath()); // 打印绝对路径
			System.out.println("文件大小：" + f.length());
			System.out.println("文件已存在，不能再创建。");
			return;
		} else {
			f.getParentFile().mkdirs();
			// getParentFile()返回File类型的父路径，mkdirs()创建所有的路径
			try {
				f.createNewFile(); // 至此真正在硬盘上创建了myTest.txt文件。
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("package ").append(daoUrl);
		buffer.append(";").append(RT_2);
		buffer.append("import org.springframework.beans.factory.annotation.Autowired;").append(RT_1);
		buffer.append("import org.springframework.stereotype.Service;").append(RT_1);
		buffer.append("import com.oo.businessplan.basic.service.impl.BaseServiceImpl;").append(RT_1);
		buffer.append("import ").append(getPackageName(cName)).append(".mapper.");
		buffer.append(beanName).append("Mapper;").append(RT_1);
		buffer.append("import ").append(getPackageName(cName)).append(".service.");
		buffer.append(beanName).append("Service;").append(RT_1);
		buffer.append("import ").append(cName).append(";").append(RT_1);

		buffer.append(RT_2).append(ANNOTATION.replace(ANNOTATION_AUTHOR_NAME, authorName));
		buffer.append("@Service(\"").append(getLowercaseChar(beanName)).append("Service\")");
		buffer.append(RT_1);
		buffer.append("public class ").append(getLastChar(cName));
		buffer.append("ServiceImpl extends BaseServiceImpl<").append(getLastChar(cName));
		buffer.append("> implements ").append(getLastChar(cName)).append("Service {");

		buffer.append(RT_2).append(BLANK_4).append("@Autowired");
		buffer.append(RT_1).append(BLANK_4).append(beanName);
		buffer.append("Mapper ").append(getLowercaseChar(beanName)).append("Mapper;");

		buffer.append(RT_1).append("}");
		FileWriter fw = new FileWriter(f);
		fw.write(buffer.toString());
		fw.flush();
		fw.close();
		showInfo(fileName);
	}

	/**
	 * 创建bean的Service<br>
	 * 
	 * @param c
	 * @throws Exception
	 */
	public void createBeanService(Class c) throws Exception {
		String cName = c.getName();
		String daoUrl = getPackageName(cName) + ".service";
		String daoPath = daoUrl.replace(".", "/");
		String fileName = System.getProperty("user.dir") + "/src/main/java/" + daoPath + "/" + getLastChar(cName)
				+ "Service.java";
		File f = new File(fileName);
		if (f.exists()) { // 如果已经存在f文件了
			System.out.println("文件名：" + f.getAbsolutePath()); // 打印绝对路径
			System.out.println("文件大小：" + f.length());
			System.out.println("文件已存在，不能再创建。");
			return;
		} else {
			f.getParentFile().mkdirs();
			// getParentFile()返回File类型的父路径，mkdirs()创建所有的路径
			try {
				f.createNewFile(); // 至此真正在硬盘上创建了myTest.txt文件。
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("package ").append(daoUrl);
		buffer.append(";").append(RT_2);
		buffer.append("import com.oo.businessplan.basic.service.BaseService;").append(RT_1);
		buffer.append("import ").append(cName).append(";").append(RT_1);

		buffer.append(RT_2).append(ANNOTATION.replace(ANNOTATION_AUTHOR_NAME, authorName));
		buffer.append("public interface ").append(getLastChar(cName));
		buffer.append("Service extends BaseService<").append(getLastChar(cName));
		buffer.append("> {");
		buffer.append(RT_1).append("}");
		FileWriter fw = new FileWriter(f);
		fw.write(buffer.toString());
		fw.flush();
		fw.close();
		showInfo(fileName);
	}

	/**
	 * 创建bean的Control<br>
	 * 
	 * @param c
	 * @throws Exception
	 */
	public void createBeanControl(Class c) throws Exception {
		String cName = c.getName();
		String beanName = getLastChar(cName);
		String lowName = getLowercaseChar(beanName);
		String moduleName = getModuleName(cName);
		
		String daoUrl = getPackageName(cName) + ".controller";
		String daoPath = daoUrl.replace(".", "/");
		String jspPath = getPackageName(cName).replace(".", "/");
		jspPath = jspPath.substring(4);
		String fileName = System.getProperty("user.dir") + "/src/main/java/" + daoPath + "/" + getLastChar(cName) + "Controller.java";
		File f = new File(fileName);
		if (f.exists()) { // 如果已经存在f文件了
			System.out.println("文件名：" + f.getAbsolutePath()); // 打印绝对路径
			System.out.println("文件大小：" + f.length());
			System.out.println("文件已存在，不能再创建。");
			return;
		} else {
			f.getParentFile().mkdirs();
			// getParentFile()返回File类型的父路径，mkdirs()创建所有的路径
			try {
				f.createNewFile(); // 至此真正在硬盘上创建了myTest.txt文件。
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("package ").append(daoUrl);
		buffer.append(";").append(RT_2);
		buffer.append("import java.util.List;").append(RT_1);
		buffer.append("import java.util.Map;").append(RT_1);
		buffer.append("import java.util.HashMap;").append(RT_1);
		buffer.append("import javax.servlet.http.HttpServletRequest;").append(RT_2);
		
		buffer.append("import org.springframework.beans.factory.annotation.Autowired;").append(RT_1);
		buffer.append("import org.springframework.web.bind.annotation.PathVariable;").append(RT_1);
		buffer.append("import org.springframework.web.bind.annotation.RequestBody;").append(RT_1);
		buffer.append("import org.springframework.web.bind.annotation.DeleteMapping;").append(RT_1);
		buffer.append("import org.springframework.web.bind.annotation.GetMapping;").append(RT_1);
		buffer.append("import org.springframework.web.bind.annotation.PostMapping;").append(RT_1);
		buffer.append("import org.springframework.web.bind.annotation.RequestMapping;").append(RT_1);
		buffer.append("import org.springframework.web.bind.annotation.RequestMethod;").append(RT_1);
		buffer.append("import org.springframework.web.bind.annotation.RequestParam;").append(RT_1);
		buffer.append("import org.springframework.web.bind.annotation.RestController;").append(RT_1);
		
		buffer.append("import com.oo.businessplan.basic.controller.BaseController;").append(RT_1);
		buffer.append("import com.oo.businessplan.common.pageModel.ResponseResult;").append(RT_1);
		buffer.append("import com.oo.businessplan.common.security.IgnoreSecurity;").append(RT_1);
		
		buffer.append("import ").append(getPackageName(cName)).append(".service.");
		buffer.append(beanName).append("Service;").append(RT_1);
		buffer.append("import ").append(cName).append(";").append(RT_1);

		buffer.append(RT_2).append(ANNOTATION.replace(ANNOTATION_AUTHOR_NAME, authorName));
		buffer.append("@RestController").append(RT_1);
		buffer.append("@RequestMapping(value = \"/api/").append(moduleName).append("/").append(lowName).append("\")").append(RT_1);
		buffer.append("public class ").append(beanName);
		buffer.append("Controller extends BaseController{");
		buffer.append(RT_2).append(BLANK_4).append("@Autowired");
		buffer.append(RT_1).append(BLANK_4).append(beanName);
		buffer.append("Service ").append(lowName).append("Service;");

		buffer.append(RT_1).append(BLANK_4).append("@IgnoreSecurity");
		buffer.append(RT_1).append(BLANK_4).append("@GetMapping(value = \"/list.re\")");
		buffer.append(RT_1).append(BLANK_4).append("public ResponseResult<List<").append(beanName).append(">> list(HttpServletRequest request) {");
		buffer.append(RT_1).append(BLANK_8).append("ResponseResult<List<").append(beanName).append(">> response = new ResponseResult<>();");
		buffer.append(RT_1).append(RT_1).append(BLANK_8).append("return response.success();");
	    buffer.append(RT_1).append(BLANK_4).append("}");
	    
	    
	    /*buffer.append(RT_2).append(BLANK_4).append("@ApiOperation(value = \"查找").append(tableDes).append("列表(分页)\")");
		buffer.append(RT_1).append(BLANK_4).append("@IgnoreSecurity(val = false)");
		buffer.append(RT_1).append(BLANK_4).append("@RequestMapping(value = \"/page\", method = RequestMethod.GET)");
		buffer.append(RT_1).append(BLANK_4).append("public Response page(HttpServletRequest request,");
		buffer.append("@ApiParam(value = \"页码\", required = true) @RequestParam(required = true, value = \"pageNum\") Integer pageNum,");
		buffer.append("@ApiParam(value = \"每页显示数量\", required = true) @RequestParam(required = true, value = \"pageSize\") Integer pageSize,");
		buffer.append("@ApiParam(value = \"关键字\", required = false) @RequestParam(required = false, value = \"keyword\") String keyword) {");
		buffer.append(RT_1).append(BLANK_8).append("Map map = new HashMap();");
		buffer.append(RT_1).append(BLANK_8).append("map.put(\"keyword\", keyword);");
		buffer.append(RT_1).append(BLANK_8).append("map.put(\"tenantId\", getTenantId(request));");
		buffer.append(RT_1).append(BLANK_8).append("PageInfo<Map> page = ").append(lowName).append("Service.findListPageByMap(map,pageNum,pageSize);");
		buffer.append(RT_1).append(BLANK_8).append("Response response = new Response();");
		buffer.append(RT_1).append(BLANK_8).append("return response.success(page);");
	    buffer.append(RT_1).append(BLANK_4).append("}");
		
		buffer.append(RT_2).append(BLANK_4).append("@ApiOperation(value = \"根据Id查找").append(tableDes).append("\")");
		buffer.append(RT_1).append(BLANK_4).append(" @IgnoreSecurity(val = false)");
		buffer.append(RT_1).append(BLANK_4).append("@RequestMapping(value = \"/{id}\", method = RequestMethod.GET)");
		buffer.append(RT_1).append(BLANK_4).append("public Response get(HttpServletRequest request,");
		buffer.append("@ApiParam(value = \"id\", required = true) @PathVariable(required = true, value = \"id\") Integer id){");
		buffer.append(RT_1).append(BLANK_8).append(beanName).append(" ").append(lowName).append(" = new ").append(beanName).append("();");
		buffer.append(RT_1).append(BLANK_8).append(lowName).append(".setId(id);");
		buffer.append(RT_1).append(BLANK_8).append("return getResult(").append(lowName).append("Service.getMapById(").append(lowName).append("));");
	    buffer.append(RT_1).append(BLANK_4).append("}");
		
	    buffer.append(RT_2).append(BLANK_4).append("@ApiOperation(value =\"新增").append(tableDes).append("\")");
	    buffer.append(RT_1).append(BLANK_4).append("@IgnoreSecurity(val = false)");
	    buffer.append(RT_1).append(BLANK_4).append("@RequestMapping(method = RequestMethod.POST)");
	    buffer.append(RT_1).append(BLANK_4).append("public Response save(HttpServletRequest request,");
	    buffer.append("@RequestBody ").append(beanName).append(" ").append(lowName).append(") throws UnsupportedEncodingException, NoSuchAlgorithmException {");
	    buffer.append(RT_1).append(BLANK_8).append(lowName).append(".setCreater(getEmployeeName(request));");
	    buffer.append(RT_1).append(BLANK_8).append(lowName).append(".setCreaterId(getEmployeeId(request));");
	    buffer.append(RT_1).append(BLANK_8).append(lowName).append(".setModify(").append(lowName).append(".getCreater());");
	    buffer.append(RT_1).append(BLANK_8).append(lowName).append(".setModifyId(").append(lowName).append(".getCreaterId());");
	    buffer.append(RT_1).append(BLANK_8).append(lowName).append(".setTenantId(getTenantId(request));");
	    buffer.append(RT_1).append(BLANK_8).append("return getResult(").append(lowName).append("Service.add(").append(lowName).append("));");
	    buffer.append(RT_1).append(BLANK_4).append("}");

	    buffer.append(RT_2).append(BLANK_4).append("@ApiOperation(value = \"修改").append(tableDes).append("\")");
	    buffer.append(RT_1).append(BLANK_4).append("@IgnoreSecurity(val = false)");
	    buffer.append(RT_1).append(BLANK_4).append("@RequestMapping(method = RequestMethod.PATCH)");
	    buffer.append(RT_1).append(BLANK_4).append("public Response update(HttpServletRequest request,");
	    buffer.append("@RequestBody ").append(beanName).append(" ").append(lowName).append("Form) throws UnsupportedEncodingException, NoSuchAlgorithmException {");
	    buffer.append(RT_1).append(BLANK_8).append("if(").append(lowName).append("Form.getId() <= 0)");
	    buffer.append(RT_1).append(BLANK_8).append("{");
	    buffer.append(RT_1).append(BLANK_12).append("return getResultError(ValidationMessge.ID_RRROR);");
        buffer.append(RT_1).append(BLANK_8).append("}");
	    buffer.append(RT_1).append(BLANK_8).append(beanName).append(" ").append(lowName).append(" = new ").append(beanName).append("();");
        buffer.append(RT_1).append(BLANK_8).append(lowName).append(".setId(").append(lowName).append("Form.getId());");
        buffer.append(RT_1).append(BLANK_8).append(lowName).append(" = ").append(lowName).append("Service.getById(").append(lowName).append(");");
        buffer.append(RT_1).append(BLANK_8).append("if(").append(lowName).append(" == null)");
	    buffer.append(RT_1).append(BLANK_8).append("{");
	    buffer.append(RT_1).append(BLANK_12).append("return getResultError(ValidationMessge.QUERY_ERROR);");
        buffer.append(RT_1).append(BLANK_8).append("}");
        buffer.append(RT_1).append(BLANK_8).append(lowName).append(".setModify(getEmployeeName(request));");
	    buffer.append(RT_1).append(BLANK_8).append(lowName).append(".setModifyId(getEmployeeId(request));");
	    buffer.append(RT_1).append(BLANK_8).append("return getResult(").append(lowName).append("Service.update(").append(lowName).append("));");
	    buffer.append(RT_2).append(BLANK_4).append("}");
	    
	    buffer.append(RT_2).append(BLANK_4).append("@ApiOperation(value = \"删除").append(tableDes).append("\")");
	    buffer.append(RT_1).append(BLANK_4).append("@IgnoreSecurity(val = false)");
	    buffer.append(RT_1).append(BLANK_4).append("@RequestMapping(method = RequestMethod.DELETE)");
	    buffer.append(RT_1).append(BLANK_4).append("public Response delete(HttpServletRequest request,");
	    buffer.append("@ApiParam(value = \"ids\", required = true) @RequestParam(required = true, value = \"ids\") Integer ids){");
	    buffer.append(RT_1).append(BLANK_8).append("Map map = new HashMap();");
	    buffer.append(RT_1).append(BLANK_8).append("map.put(\"ids\", ids);");
	    buffer.append(RT_1).append(BLANK_8).append("map.put(\"modify\", getEmployeeName(request));");
	    buffer.append(RT_1).append(BLANK_8).append("map.put(\"modifyId\", getEmployeeId(request));");
	    buffer.append(RT_1).append(BLANK_8).append("return getResult(").append(lowName).append("Service.delete(map));");
	    buffer.append(RT_1).append(BLANK_4).append("}");*/

		buffer.append(RT_1).append("}");
		FileWriter fw = new FileWriter(f);
		fw.write(buffer.toString());
		fw.flush();
		fw.close();
		showInfo(fileName);
	}

	/**
	 * 创建bean的Xml<br>
	 * 
	 * @param c
	 * @throws Exception
	 */
	public void createBeanXml(Class c, String idName, Class type) throws Exception {
		String cName = c.getName();
		String beanName = getLastChar(cName);
		String moduleName = getModuleName(cName);
		String packageName = getPackageName(cName);
        
		String fileName = System.getProperty("user.dir") + "/src/main/resources/mapper/" + moduleName + "/"
				+ getLastChar(cName) + "Mapper.xml";
		File f = new File(fileName);

		if (f.exists()) { // 如果已经存在f文件了
			System.out.println("文件名：" + f.getAbsolutePath()); // 打印绝对路径
			System.out.println("文件大小：" + f.length());
			System.out.println("文件已存在，不能再创建。");
			return;
		} else {
			f.getParentFile().mkdirs();
			// getParentFile()返回File类型的父路径，mkdirs()创建所有的路径
			try {
				f.createNewFile(); // 至此真正在硬盘上创建了myTest.txt文件。
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		List<List<fieldMap>> args = fingByArg(c);
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append(RT_1).append(
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		buffer.append(RT_1).append("<mapper namespace=\"").append(packageName).append(".mapper.").append(beanName)
				.append("Mapper\">");
		
		String a = beanName.substring(0,2).toLowerCase();
        String alia = beanName.toLowerCase();
		buffer.append(RT_2).append(BLANK_4).append(" <resultMap id=\"").append(beanName);
		buffer.append("Map\" type=\"").append(alia).append("\">");
		buffer.append(RT_1).append(BLANK_8).append("<id property=\"id\" column=\"id\"/>");
		for (fieldMap arg : args.get(0)) {
			if (!arg.equals("id")) {
				buffer.append(RT_1).append(BLANK_8).append("<result property=\"").append(arg.field.getName());
				buffer.append("\" column=\"").append(arg.field.getName()).append("\"/>");
			}
		}
		if ( args.get(1).size() > 0) {
			for (fieldMap arg : args.get(1)) {
				buffer.append(RT_1).append(BLANK_8).append("<association property=\"").append(arg.field.getName());
				buffer.append("\" javaType=\"").append(arg.cls.getSimpleName().toLowerCase()).append("\">");
				buffer.append(RT_1).append(BLANK_12).append("<id property=\"id\" column=\"id\"/>");
				for (Field tf : arg.childs) {
					if (!tf.getName().equals("id")) {
						buffer.append(RT_1).append(BLANK_12).append("<result property=\"").append(tf.getName());
						buffer.append("\" column=\"").append(tf.getName()).append("\"/>");
					}				
				}
				buffer.append(RT_1).append(BLANK_8).append("</association>");
			}
		}
		if ( args.get(2).size() > 0) {
			for (fieldMap arg : args.get(2)) {
				buffer.append(RT_1).append(BLANK_8).append("<collection property=\"").append(arg.field.getName());
				buffer.append("\" ofType=\"").append(arg.cls.getSimpleName().toLowerCase()).append("\">");
				buffer.append(RT_1).append(BLANK_12).append("<id property=\"id\" column=\"id\"/>");
				for (Field tf : arg.childs) {
					if (!tf.getName().equals("id")) {
						buffer.append(RT_1).append(BLANK_12).append("<result property=\"").append(tf.getName());
						buffer.append("\" column=\"").append(tf.getName()).append("\"/>");
					}				
				}
				buffer.append(RT_1).append(BLANK_8).append("</collection>");
			}
		}
		buffer.append(RT_1).append(BLANK_4).append("</resultMap>");

		buffer.append(RT_2).append(BLANK_4).append(" <sql id=\"base_column\">");
		buffer.append(RT_1).append(BLANK_8);
		buffer.append(a).append(".id,");
		List<fieldMap> fms = args.get(0);
		for (int i = 0; i < fms.size(); i++) {
			if (fms.get(i).field.getName().equals("serialVersionUID")) {
				continue;
			}
			buffer.append(a).append(".").append(fms.get(i).field.getName());
			if (i < fms.size() - 1) {
				buffer.append(",");
			}
		}
		
		buffer.append(RT_1).append(BLANK_4).append(" </sql>");
	
        //add方法
		buffer.append(RT_2).append(BLANK_4).append("<insert id=\"add\" useGeneratedKeys=\"true\" keyProperty=\"").append("id").append("\">");
		buffer.append(RT_1).append(BLANK_8).append("INSERT INTO ").append(tableName);
		buffer.append(RT_1).append(BLANK_8).append("(");
		buffer.append(RT_1).append(BLANK_12).append("<trim suffixOverrides=\",\">");
		for (int i = 0; i < fms.size(); i++) {
				buffer.append(RT_1).append(BLANK_16)
				.append("<if test=\"").append(fms.get(i).field.getName()).append(" != null \">");
				buffer.append(RT_1).append(BLANK_16).append(fms.get(i).field.getName());
				if (i < fms.size() - 1) {
					buffer.append(",");
				}
				buffer.append(RT_1).append(BLANK_16).append("</if>");				
		}
		buffer.append(RT_1).append(BLANK_12).append("</trim>");
		buffer.append(RT_1).append(BLANK_8).append(")");
		buffer.append(RT_1).append(BLANK_8).append("VALUES (");
		buffer.append(RT_1).append(BLANK_12).append("<trim suffixOverrides=\",\">");
		for (int i = 0; i < fms.size(); i++) {
				buffer.append(RT_1).append(BLANK_16)
				.append("<if test=\"").append(fms.get(i).field.getName()).append(" != null \">");
				buffer.append(RT_1).append(BLANK_16)
				      .append("#{").append(fms.get(i).field.getName());
				if (fms.get(i).field.getName().equals("delflag")||fms.get(i).field.getName().equals("state")) {
					buffer.append(",jdbcType=TINYINT");
				}
				buffer.append("}");
				if (i < fms.size() - 1) {
					buffer.append(",");
				}
				buffer.append(RT_1).append(BLANK_16).append("</if>");				
		}
		buffer.append(RT_1).append(BLANK_12).append("</trim>");
		buffer.append(RT_1).append(BLANK_8).append(")");
		buffer.append(RT_1).append(BLANK_4).append("</insert>");
		
		//delete方法
		buffer.append(RT_2).append(BLANK_4).append("<update id=\"delete\" ").append(">");
		buffer.append(RT_1).append(BLANK_8).append("UPDATE ").append(tableName);
		buffer.append(" set delflag = #{delflag}");
		if (contain(fms,"modifier")) {
			buffer.append(" , modifier = #{modifier},modifierTime = {modifierTime}");
		}
		buffer.append(RT_1).append(BLANK_8).append(" WHERE id = #{ id }");
		buffer.append(RT_1).append(BLANK_4).append("</update>");
		
		//state方法
		if (contain(fms,"state")) {
			buffer.append(RT_2).append(BLANK_4).append("<update id=\"state\" ").append(">");
			buffer.append(RT_1).append(BLANK_8).append("UPDATE ").append(tableName);
			buffer.append(" set state = #{state}");
			if (contain(fms,"modifier")) {
				buffer.append(" , modifier = #{modifier},modifierTime = {modifierTime}");
			}
			buffer.append(RT_1).append(BLANK_8).append(" WHERE id = #{ key })");
			buffer.append(RT_1).append(BLANK_4).append("</update>");
		}

		/*
		 * update
		 */
		buffer.append(RT_2).append(BLANK_4).append("<update id=\"update\" parameterType=\"");
		buffer.append(alia).append("\">");
		buffer.append(RT_1).append(BLANK_8).append("UPDATE ").append(tableName).append(" SET ");
		buffer.append(RT_1).append(BLANK_12).append("<trim suffixOverrides=\",\">");
		for (int i = 0; i < fms.size(); i++) {
				buffer.append(RT_1).append(BLANK_16).append("<if test=\"").append(fms.get(i).field.getName()).append(" != null \">");				
				buffer.append(RT_1).append(BLANK_16).append(fms.get(i).field.getName()).append(" = ")
				      .append("#{").append(fms.get(i).field.getName());
				if (fms.get(i).field.getName().equals("delflag")||fms.get(i).field.getName().equals("state")) {
					buffer.append(",jdbcType=TINYINT");
				}
				buffer.append("}");
				if (i < fms.size() - 1) {
					buffer.append(",");
				}
				buffer.append(RT_1).append(BLANK_16).append("</if>");
		}
		buffer.append(RT_1).append(BLANK_12).append("</trim>");
		buffer.append(RT_1).append(BLANK_8).append(" WHERE id = #{id}");
		buffer.append(RT_1).append(BLANK_4).append("</update>");
		
		/*
		 * updateFull
		 */
		buffer.append(RT_2).append(BLANK_4).append("<update id=\"updateFull\" parameterType=\"");
		buffer.append(alia).append("\">");
		buffer.append(RT_1).append(BLANK_8).append("UPDATE ").append(tableName).append(" SET ");
		for (int i = 0; i < fms.size(); i++) {			
				buffer.append(RT_1).append(BLANK_12).append(fms.get(i).field.getName()).append(" = ")
				      .append("#{").append(fms.get(i).field.getName());
				if (fms.get(i).field.getName().equals("delflag")||fms.get(i).field.getName().equals("state")) {
					buffer.append(",jdbcType=TINYINT");
				}
				buffer.append("}");
				if (i < fms.size() - 1) {
					buffer.append(",");
				}
		}
		buffer.append(RT_1).append(BLANK_8).append(" WHERE id = #{id}");
		buffer.append(RT_1).append(BLANK_4).append("</update>");
		
		/*
		 * getById
		 */
		buffer.append(RT_2).append(BLANK_4).append("<select id=\"getById\" resultType=\"");
		buffer.append(alia).append("\" parameterType=\"").append(alia).append("\">");
		buffer.append(RT_1).append(BLANK_8).append("SELECT ");
		buffer.append(RT_1).append(BLANK_8).append("<include refid=\"base_column\"/>");
		buffer.append(RT_1).append(BLANK_8).append("FROM ").append(tableName).append(BLANK_1).append(a);
		buffer.append(RT_1).append(BLANK_8).append("WHERE ").append(a).append(".")
		      .append("id = #{id} AND ").append(a).append(".")
		      .append("delflag = #{delflag}");
		buffer.append(RT_1).append(BLANK_4).append("</select>");
		
		
		buffer.append(RT_2).append(BLANK_4).append("<select id=\"getList\" resultType=\"");
		buffer.append(alia).append("\" parameterType=\"").append(alia).append("\">");
		buffer.append(RT_1).append(BLANK_8).append("SELECT ");
		buffer.append(RT_1).append(BLANK_8).append("<include refid=\"base_column\"/>");
		buffer.append(RT_1).append(BLANK_8).append("FROM ").append(tableName).append(BLANK_1).append(a);
		buffer.append(RT_1).append(BLANK_8).append("WHERE ");
		buffer.append(RT_1).append(BLANK_4).append("<trim prefixOverrides=\"AND\">");
		for (int i = 0; i < fms.size(); i++) {
			buffer.append(RT_1).append(BLANK_8).append("<if test=\"").append(" != null \">");
			if (fms.get(i).field.getName().equals("state")||fms.get(i).field.getName().equals("delflag")) {
				buffer.append(RT_1).append(BLANK_8).append("AND ")
			      .append(a).append(".`").append(fms.get(i).field.getName()).append("` = #{").append(fms.get(i).field.getName()).append(",jdbcType=TINYINT}");
				buffer.append(RT_1).append(BLANK_8).append("</if>");
				continue;
			}
			buffer.append(RT_1).append(BLANK_8).append("AND ")
			      .append(a).append(".`").append(fms.get(i).field.getName()).append("` = #{").append(fms.get(i).field.getName()).append("}");
			buffer.append(RT_1).append(BLANK_8).append("</if>");
		}		
		buffer.append(RT_1).append(BLANK_4).append("</trim>");
		buffer.append(RT_1).append(BLANK_4).append("</select>");
		

		buffer.append(RT_2).append("</mapper>");
		FileWriter fw = new FileWriter(f);
		fw.write(buffer.toString());
		fw.flush();
		fw.close();
		showInfo(fileName);
	}

	/**
	 * 获取路径的最后面字符串<br>
	 * 如：<br>
	 * <code>str = "com.lesso.sys.pojo.SysUser"</code><br>
	 * <code> return "com.lesso.sys";<code>
	 * 
	 * @param str
	 * @return
	 */
	public String getPackageName(String str) {
		if (str != null && str.length() > 0) {
			int dot = str.indexOf(".", 20);
			if (dot > -1 && dot < str.length() - 1) {
				return str.substring(0, dot);
			}
		}
		return str;
	}

	/**
	 * 获取路径的最后面字符串<br>
	 * 如：<br>
	 * <code>str = "com.lesso.sys.pojo.SysUser"</code><br>
	 * <code> return "sys";<code>
	 * 
	 * @param str
	 * @return
	 */
	public String getModuleName(String str) {
		if (str != null && str.length() > 0) {
			String[] dot = str.split("\\.");
			str = dot[3];
		}
		return str;
	}

	/**
	 * 获取路径的最后面字符串<br>
	 * 如：<br>
	 * <code>str = "com.sys.structure.beans.SysUser"</code><br>
	 * <code> return "SysUser";<code>
	 * 
	 * @param str
	 * @return
	 */
	public String getLastChar(String str) {
		if (str != null && str.length() > 0) {
			int dot = str.lastIndexOf('.');
			if (dot > -1 && dot < str.length() - 1) {
				return str.substring(dot + 1);
			}
		}
		return str;
	}

	/**
	 * 把第一个字母变为小写<br>
	 * 如：<br>
	 * <code>str = "UserDao";</code><br>
	 * <code>return "userDao";</code>
	 * 
	 * @param str
	 * @return
	 */
	public String getLowercaseChar(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	/**
	 * 显示信息
	 * 
	 * @param info
	 */
	public void showInfo(String info) {
		System.out.println("创建文件：" + info + "成功！");
	}

	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public static String getDate() {

		return DateUtil.formatyyyy_MM_dd_HH_mm_ss(new Date());
	}
	
	private class fieldMap {
		
		public fieldMap (Class cls, Field field) {
			this.cls = cls;
			this.field = field;
		}
		
		Class cls;
		Field field;
		List<Field> childs = new ArrayList<>();
		
		public String toString () {
			String cstr = "";
			for (int i=0;i<childs.size();i++) {
			  cstr +=","+childs.get(i).getName();
			}
			return field.getName() +":childs:" +cstr;
		}
	}

	/**
	 * 使用Java反射来获取javaBean的参数值， 将参数值拼接为操作内容
	 */
	public List<List<fieldMap>> fingByArg(Class c) throws Exception {
        
		List<List<fieldMap>> list = new ArrayList<>(3);
		do {
			list.add(new ArrayList<fieldMap>());
		} while (list.size()<3);
		List<Field> fields = new ArrayList<>();
		findSupClass(c, fields);
		fields.forEach(o->System.out.println(o.getName()+":"+o.getType().getName()));
		Type temp = null;
		for (Field o : fields) {
			if (checkBasicType(o.getType())) {
				list.get(0).add(new fieldMap(o.getType(), o));
			} else if (o.getType().getSimpleName().equals("String[]") || o.getType().getSimpleName().equals("List") || o.getType().getSimpleName().equals("Set")) {
				temp = o.getGenericType();
				
				if (temp instanceof ParameterizedType) {
					ParameterizedType p = (ParameterizedType)temp;
					Class cc = ((Class)p.getActualTypeArguments()[0]);
					fieldMap fi = new fieldMap(cc, o);
					list.get(2).add(fi);
					List<Field> childs = new ArrayList<>();
					findSupClass(cc, childs);
					for (Field ch : childs) {
						if (checkBasicType(ch.getType())) {
							fi.childs.add(ch);
						}
					}
					//list.get(2).add();
				} 
			} else {
                   temp = o.getGenericType();				
				   if (temp instanceof ParameterizedType) {
					ParameterizedType p = (ParameterizedType)temp;
					Class cc = ((Class)p.getActualTypeArguments()[0]);
					fieldMap fi = new fieldMap(cc, o);
					list.get(1).add(fi);
					List<Field> childs = new ArrayList<>();
					findSupClass(cc, childs);
					for (Field ch : childs) {
						if (checkBasicType(ch.getType())) {
							fi.childs.add(ch);
						}
					}
					//list.get(2).add();
				} 
			}
		}
		/*for (Field o : fields) {
			String type = o.getType().getSimpleName();
			switch (type) {
			case "Integer":
			case "int":
			case "Byte":
			case "byte":
			case "Timestamp":
			case "Date":
			case "Long":
			case "long":
			case "Double":
			case "double":
			case "BigDecimal":
			case "Float":
			case "Boolean":
			case "boolean":
			case "String":
				list.get(0).add(o.getType());
				break;
			case "String[]":
			case "List":
			case "Set":
				temp = o.getGenericType();
				if (temp instanceof ParameterizedType) {
					ParameterizedType p = (ParameterizedType)temp;
					list.get(2).add(((Class)p.getActualTypeArguments()[0]));
				}
				break;
			default:
				list.get(1).add(o.getType());
				break;
			}
		}*/
		
		return list;
	}
	
	private String[] basetypes = {"Integer","int","Byte","byte","Timestamp", "Date", "Long", "long", "Double", "double", "BigDecimal", "Float", "Boolean", "boolean", "String"};
	
	private boolean checkBasicType (Class type) {
		for (int i=0;i<basetypes.length;i++) {
            if (basetypes[i].equals(type.getSimpleName())) {
            	return true;
            }
		}
		return false;
	}
	
	/**
	 * 逐级查找属性
	 * @param c
	 * @param fields
	 */
	private void findSupClass(Class c, List<Field> fields) {
		if (c == Object.class) {
			return;
		}		
		findSupClass(c.getSuperclass(), fields);
		Field[] fs = c.getDeclaredFields();
		for (int i=0; i < fs.length; i++) {
			if (fs[i].getName().equals("serialVersionUID")) {
				continue;
			}
			fields.add(fs[i]);
		}		
	}

	/**
	 * 使用Java反射来获取javaBean的参数值， 将参数值拼接为操作内容
	 */
	public String[] fingByArgs(Class c) throws Exception {

		Field[] fields = c.getDeclaredFields();
		String[] rs = new String[fields.length];
		int i = 0;
		for (Field f : fields) {
			if (f.getName().toLowerCase().indexOf("id") == -1) {
				FieldMeta meta = f.getAnnotation(FieldMeta.class);
				if (meta != null) {
					f.setAccessible(true);
					// System.out.println(meta.name() + f.getName() + f.get(info));
					// 将值加入内容中
					rs[i] = f.getName() + "_" + meta.name();
					i++;
				}
			}

		}

		return rs;
	}
	
	public boolean contain(List<fieldMap> args,String str){
		for (int i = 0; i < args.size(); i++) {
			if(args.get(i).field.getName().equals(str)){
				return true;
			}
		}
		return false;
	}
}
