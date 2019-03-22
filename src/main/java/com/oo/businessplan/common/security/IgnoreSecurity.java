package com.oo.businessplan.common.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 权限校检注解
 * @author admin
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreSecurity {
    
	/**
	 * 判断是否校验用户登陆状况
	 * @return
	 */
	boolean val() default false;

	/**
	 * 判断是否启用权限校验
	 * 设置default false 可以达到非必填项的效果
	 * @return
	 */
	boolean authority() default true;
}
