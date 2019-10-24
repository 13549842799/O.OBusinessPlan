package com.oo.businessplan.common.valid;

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
    
    String key() default "";
    
    /**
     * the annotation checkout the value is not null defaultily
     * @return
     */
    boolean notNull() default true;
    
    String notNullMess() default "";
    
    /**
     * if the value is -1 ,it main that it don't checkout the length,
     * the field can automatic recognize the value type, if the value is number, the max main the number can't over the max,
     * if the value is string, it main the value's length can't over the max
     * @return
     */
    String max() default "";
    
    String maxMess() default "";
    
    String min() default "";
    
    String minMess() default "";
    
    String commonMess() default "";
}
