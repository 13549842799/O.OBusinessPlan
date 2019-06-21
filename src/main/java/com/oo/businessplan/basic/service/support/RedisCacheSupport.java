package com.oo.businessplan.basic.service.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.oo.businessplan.basic.mapper.RedisCacheMapper;
import com.oo.businessplan.basic.service.RedisCacheService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.constant.EntityConstants;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.redis.RedisTokenManager;

/**
 * redis缓存相关的抽象类
 * @author cyz
 *
 * @param <T>
 */
public abstract class RedisCacheSupport<T> extends BaseServiceImpl<T> implements RedisCacheService<T> {
	
	@Autowired
    protected RedisTokenManager tokenManager;
	
	@Autowired
	protected RedisCacheMapper<T> redisMapper;
	
	private final HKeyMap hkey = new HKeyMap();
	
	
	/**
	 * 从redis中获取key为key，hashkey为hashkey的对象，如果没有，则从数据库中获取
	 * 符合要求，符合状态的记录，然后保存在redis中
	 * 在redis中
	 * @param key
	 * @param state
	 * @param expired
	 * @param timeUnit
	 * @return
	 */
	public final  T getObject(String key,Byte state,long expired,TimeUnit timeUnit, Map<String, Object> otherParams){
		String objName = this.getClass().getSimpleName();
		@SuppressWarnings("unchecked")
		T t =(T)tokenManager.getValueFromMap(key, hkey.get(objName), expired, timeUnit);
		if (t==null) {
			Map<String, Object> params = new HashMap<>();
			if (otherParams != null) {
				params.putAll(otherParams);
			}
			params.put("key", key);
			params.put("delflag", DeleteFlag.VALID.getCode());
			if (state != null) {
				params.put("state", state);
			}
			t = redisMapper.getByStr(params);
			tokenManager.saveForMap(key, hkey.get(objName), t, expired, timeUnit);
		}
		
	    return t;
	}
	
	/**
	 * 与上面的方法类似，不过在从数据库中获取记录时不需加入状态条件
	 * @param key
	 * @param expired
	 * @param timeUnit
	 * @return
	 */
	public final  T getObject(String key,long expired,TimeUnit timeUnit, Map<String, Object> otherParams){
		 
		T t =getObject(key, null, expired, timeUnit, otherParams);
		
	    return t;
	}
	
	/**
	 * 
	 * @param key
	 * @param state
	 * @param expired
	 * @param timeUnit
	 * @return
	 */
	private final  List<T> getListObject(String key,Byte state,long expired,TimeUnit timeUnit){
		String objName = this.getClass().getSimpleName();
		@SuppressWarnings("unchecked")
		List<T> t =(List<T>)tokenManager.getValueFromMap(key, hkey.get(objName), expired, timeUnit);
		if (t==null) {
			t = redisMapper.getListByStr(key, DeleteFlag.VALID.getCode(), state);
			tokenManager.saveForMap(key, hkey.get(objName), t, expired, timeUnit);
		}
		
	    return t;
	}
	
	/**
	 * 
	 * @param key
	 * @param expired
	 * @param timeUnit
	 * @return
	 */
	public final List<T> getListObject(String key,long expired,TimeUnit timeUnit){
		 
	    return getListObject(key, null, expired, timeUnit);
	}
	
	
	
	@Override
	public void saveObject(String key, T entity, long expired, TimeUnit timeUnit) {
		if (entity == null) {
			System.out.println("entity 为 null");
			return;
		}
		String objName = this.getClass().getSimpleName();
		tokenManager.saveForMap(key, hkey.get(objName), entity, expired, timeUnit);
	}

	/**
	 *  移除保存在redis中键为key的对象
	 * @param key
	 * @return
	 */
	public boolean remove(String key){
		try {
			tokenManager.cancel(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 重置hash的存在时间为0
	 * @param key
	 */
	public void resetExpire(String key) {
		tokenManager.expire(key);
	}
	
	private final class HKeyMap {
		private Map<String, String> keyMap = new HashMap<>();
		
		public HKeyMap () {
			keyMap.put("AdminServiceImpl", EntityConstants.REDIS_ADMIN);
			keyMap.put("EmployeeServiceImpl", EntityConstants.REDIS_EMPLOYEE);
			keyMap.put("AuthorityServiceImpl", EntityConstants.REDIS_AUTHORITY);
			keyMap.put("ResourceServiceImpl", EntityConstants.REDIS_RESOURCE);
		}
		
		public String get(String key) {
			return keyMap.get(key);
		}
	}

}
