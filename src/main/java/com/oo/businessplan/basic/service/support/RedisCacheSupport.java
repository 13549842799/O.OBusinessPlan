package com.oo.businessplan.basic.service.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.oo.businessplan.basic.service.RedisCacheService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
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
	public final  T getObject(String key,Byte state,int expired,int timeUnit){
		String objName = this.getClass().getSimpleName();
		@SuppressWarnings("unchecked")
		T t =(T)tokenManager.getValueFromMap(key, hkey.get(objName), expired, timeUnit);
		if (t==null) {
			t = baseMapper.getByStr(key,DeleteFlag.VALID.getCode(), state);
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
	public final  T getObject(String key,int expired,int timeUnit){
		 
		T t =getObject(key, null, expired, timeUnit);
		
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
	private final  List<T> getListObject(String key,Byte state,int expired,int timeUnit){
		String objName = this.getClass().getSimpleName();
		@SuppressWarnings("unchecked")
		List<T> t =(List<T>)tokenManager.getValueFromMap(key, hkey.get(objName), expired, timeUnit);
		if (t==null) {
			t = baseMapper.getListByStr(key, DeleteFlag.VALID.getCode(), state);
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
	public final List<T> getListObject(String key,int expired,int timeUnit){
		 
	    return getListObject(key, null, expired, timeUnit);
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
	
	private final class HKeyMap {
		private Map<String, String> keyMap = new HashMap<>();
		
		public HKeyMap () {
			keyMap.put("AdminServiceImpl", "admin");
			keyMap.put("EmployeeServiceImpl", "employee");
			keyMap.put("AuthorityServiceImpl", "authority");
			keyMap.put("ResourceServiceImpl", "resource");
		}
		
		public String get(String key) {
			return keyMap.get(key);
		}
	}

}
