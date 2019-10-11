package com.oo.businessplan.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * this annotation do for introduce the entity field
 * @author cyz
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldMeta {
	
	/**
	 * 
	 * @return
	 */
	@AliasFor("value")
	String name() default "";
	
	/**
	 * 
	 * @return
	 */
	@AliasFor("name")
	String value() default "";
	
	/**
	 * the other introduce for field
	 * @return
	 */
    String alias() default "";
}
