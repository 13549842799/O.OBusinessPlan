package create;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Flags.Flag;

import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.article.pojo.entity.Diary;
import com.oo.businessplan.authority.pojo.Role;

public class ResultMapCreator {
	
	private static final String RT_1 = "\r\n";
	private static final String BLANK_1 = " ";
	private static final String BLANK_4 = "    ";
	private static final String BLANK_8 = BLANK_4 + BLANK_4;
	private static final String BLANK_12 = BLANK_8 + BLANK_4;
	private static final String BLANK_16 = BLANK_12 + BLANK_4;
	
	private static final String[] BLANKARR = new String[]{BLANK_4,BLANK_8,BLANK_12,BLANK_16};

	public static void main(String[] args) {
		
         String string =   resultMap(Diary.class);
		 System.out.println(string);
		
	}
	
	public static String resultMap(Class<?> cls){
		
		String path = cls.getName();
		String className = path.substring(path.lastIndexOf(".")+1);
		String resultId = className+"Result";//resultMap的id  
		StringBuilder sb = new StringBuilder();
		sb.append(BLANK_4).append("<resultMap type=\"").append(path)
		    .append("\" id=\"").append(resultId).append("\">").append(RT_1);
		appendMapStr(cls, sb);
		sb.append(BLANK_4).append("</resultMap>");
		return sb.toString();
	}

	public static void appendMapStr(Class<?> cls,StringBuilder sb){
		List<Field> bufferC = new ArrayList<>();
		List<Field> bufferA = new ArrayList<>();
		Field[] fields = cls.getDeclaredFields();
		if (fields.length==0) {
			return;
		}
		Class<?> father = cls.getSuperclass();
		int start = 1;
		if (father!=null&&father != Object.class) {
			Field[] faFields  = father.getDeclaredFields();		
			String idName = faFields[0].getName();
			if (idName.equals("serialVersionUID")) {
				idName = faFields[1].getName();
				start++;
			}
			sb.append(BLANK_8).append("<id property=\"").append(idName).append("\" column=\"")
			 .append(idName).append("\"/>").append(RT_1);
			xunhuan(start, faFields, sb, bufferC, bufferA);
			start = 0;
		}else {
			String idName = fields[0].getName();
			if (idName.equals("serialVersionUID")) {
				idName = fields[1].getName();
				start++;
			}
			sb.append(BLANK_8).append("<id property=\"").append(idName).append("\" column=\"")
		      .append(idName).append("\"/>").append(RT_1);
		}		
		xunhuan(start, fields, sb, bufferC, bufferA);
		
		for (int i = 0,len = bufferA.size(); i <len; i++) {
			 Field field = bufferA.get(i);
			 Class<?> fa = field.getType();
			 String property = field.getName();
			 String clsType = fa.getName();
			 sb.append(BLANK_8).append("<association property=\"").append(property)
			    .append("\" javaType=\"").append(clsType).append("\">").append(RT_1);
			 appendMapStr(fa, sb);
			 sb.append(BLANK_8).append("</association>").append(RT_1);
		}
		
		for (int i = 0,len = bufferC.size(); i < len; i++) {
			Field field = bufferC.get(i);
			Type t = field.getGenericType();
			ParameterizedType pt = (ParameterizedType) t;
		    //得到泛型里的class类型对象  
			Class<?> fa = (Class<?>)pt.getActualTypeArguments()[0];
			String property = field.getName();
			String clsType = fa.getName();
			sb.append(BLANK_8).append("<collection property=\"").append(property)
			    .append("\" ofType=\"").append(clsType).append("\">").append(RT_1);
			appendMapStr(fa, sb);
		    sb.append(BLANK_8).append("</collection>").append(RT_1);
		}
	}
	
	public static void xunhuan(int start,Field[] fields,StringBuilder sb,List<Field> bufferC,
			List<Field> bufferA) {
		for (int i = start; i < fields.length; i++) {
			 Class<?> type = fields[i].getType();
			 String name = fields[i].getName();
			 if (name.equals("serialVersionUID")) {
				continue;
			 }
			 if (type == List.class) {
				   bufferC.add(fields[i]);
				   continue;
			 }
			 if (!(type == int.class || type == Integer.class ||
				 type == double.class || type == Double.class ||
				 type == float.class || type == Float.class ||
				 type == char.class || type == Character.class ||
				 type == byte.class || type == Byte.class ||
				 type == Timestamp.class || type == String.class ||
				 type == BigDecimal.class)) {
				 bufferA.add(fields[i]);
			 }
            sb.append(BLANK_8).append("<result property=\"").append(name).append("\" column=\"")
               .append(name).append("\"/>").append(RT_1);
		}
	}
}
