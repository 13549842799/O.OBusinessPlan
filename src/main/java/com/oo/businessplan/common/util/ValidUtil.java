package com.oo.businessplan.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * valid util
 * @author cyz
 *
 */
public class ValidUtil {
	
	/**
	 * 
	 * @param entity
	 * @param fieldName
	 * @return
	 */
	public String validNull(Object entity, String ...fieldName) throws Exception {
		
		Class<?> cls = entity.getClass();
		
		Field field = null;
		Method getM = null;
		Object val = null;
		for (String name : fieldName) {
			   
				getM = cls.getMethod(this.getName(name));


				val = getM.invoke(entity);

		}
		
		
		
		return null;
	}
	
	public String getName(String field) {
		return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}

}
