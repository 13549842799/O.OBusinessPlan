package com.oo.businessplan.basic.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis相关缓存的业务方法
 * @author cyz
 *
 */
public interface RedisCacheService<T> {
	
	/**
	 * tokenManager中时间长度数组的下标
	 */
    public static final long EXPIRED = 10l;
	
    /**
     * tokenManager中时间粒度粒度数组的下标
     */
	public static final TimeUnit TIMEUNIT = TimeUnit.DAYS;
	
	default T getObject(String key,long expired,TimeUnit timeUnit) {
		return getObject(key, expired, timeUnit, null);
	}
	
	/**
	 * 获取保存在redis中的类型为T的对象，同时更新保存的时间和时间粒度
	 * @param key  保存在redis中的key
	 * @param expired 时间长度
	 * @param timeUnit 时间粒度
	 * @return
	 */
	T getObject(String key,long expired,TimeUnit timeUnit, Map<String, Object> otherParams) ;
	
	/**
	 * 获取保存在redis中的元素类型为T的某个list集合
	 * @param key
	 * @param expired
	 * @param timeUnit
	 * @return
	 */
	 List<T> getListObject(String key,long expired,TimeUnit timeUnit);
	 
	 /**
	  * 保存实体
	  * @param key
	  * @param expired
	  * @param timeUnit
	  */
	 void saveObject(String key, T entity,long expired,TimeUnit timeUnit);
	 
	 /**
	  * 重设超时时间
	  * @param key
	  */
	 void resetExpire(String key);

}
