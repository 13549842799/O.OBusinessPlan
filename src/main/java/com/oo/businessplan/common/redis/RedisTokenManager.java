package com.oo.businessplan.common.redis;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSONObject;
import com.oo.businessplan.basic.service.RedisCacheService;
import com.oo.businessplan.common.constant.EntityConstants;
import com.oo.businessplan.common.net.SessionInfo;
import com.oo.businessplan.common.security.SessionManager;


public class RedisTokenManager extends RedisManager implements SessionManager{
	
	private Logger logger = LoggerFactory.getLogger(RedisTokenManager.class);
	
	@Resource(name="redisTemplate")
	private HashOperations<String,String,Object> hop;
	
	/**
	 * 以字符串的形式保存进redis，设置默认的超时时间和时间粒度
	 * @param key
	 * @param value
	 */
	public void saveForString(String key,Object value){		
		saveForString(key, value, expired, timeUnit);
	}
	
	/**
	 * 以字符串的形式保存进redis，设置对应的超时时间和时间粒度
	 * @param key
	 * @param value
	 * @param expired 超时时间数组的下标
	 * @param timeUnit 时间粒度数组的下标
	 */
	public void saveForString(String key,Object value,long expired,TimeUnit timeUnit){
		String strValue =parseToStr(value);
		redisTemplate.opsForValue().set(key, strValue, expired, timeUnit);
	}
	
	public void saveForMap(String key,String Hkey,Object value,long expired,TimeUnit timeUnit){
		hop.put(key, Hkey, value);
        redisTemplate.expire(key, expired, timeUnit);
	}
	
	private String parseToStr(Object value){
		return value==null?"":JSONObject.toJSONString(value);
	}
	
	/**
	 * 保存哈希结构的数据
	 * @param key       对象的键  key:{HashKey:value}
	 * @param hashKey   map的key
	 * @param expired   保存的时间长度
	 * @param timeUnit  时间粒度
	 * @return 
	 * @return
	 */
	public Object getValueFromMap(String key,String hashKey,long expired,TimeUnit timeUnit){		
		Object value =  hop.get(key, hashKey);
		if(value!=null){
	       redisTemplate.expire(key, expired, timeUnit);
		}	    
	    return value;
	}
	
	public Object getValueFromMap(String key,String hashKey){
		  return getValueFromMap(key, hashKey, expired, timeUnit);
	}
	
	/**
	 * 删除以map保存在redis中的值
	 * @param key
	 * @param hashKeys
	 */
	public void deleteHashKey(String key, Object... hashKeys) {
		hop.delete(key, hashKeys);
	}
	
	/**
	 * 更新key为key的默认过期时间
	 */
	public void expire(String key){
		expire(key, expired, timeUnit);
	}
	
	public void expire(String key,long expired,TimeUnit timeUnit){
		redisTemplate.expire(key, expired, timeUnit);
	}
	
	public void cancel(String key){
		 redisTemplate.delete(key);
	}
	
	
	public long availableTime (long expired, TimeUnit timeUnit) {
		timeUnit.name();
		return 0l;
	}
	
	/**
	 * 获取保存为字符串格式的值
	 * @param cls
	 * @param key
	 * @param expired
	 * @param timeUnit
	 * @return
	 */
    public <T> T getValue(Class<T> cls,String key,String expired,String timeUnit){		
		Object value = redisTemplate.opsForValue().get(key);
		return Objects.isNull(value)?null:cls.cast(value);
	}
	
	public Object getValue(String key){
		Object valueStr = redisTemplate.opsForValue().get(key);
		return Objects.isNull(valueStr)?null:valueStr.toString();
	}
	
	
	
	public boolean checkToken(String token, String code)  {
				
		Long alive = redisTemplate.getExpire(code);//剩余存活时间
		if (alive==0) { //检查是否过期
			return false;
		}
		
		SessionInfo info =(SessionInfo)getValueFromMap(code,EntityConstants.REDIS_SESSION_NAME);
		if (!token.equals(info.getToken())) {			
			return false;
		}
		//更新token的时间
		expire(code);	
		return true;
	}
	
	public void cancelToken(String key) {
		 cancel(key);
	}
	
	
	//===========  session 
	@Override
	public void saveSeesion(SessionInfo info, String key, long expired, TimeUnit timeUnit) {
		long availableTime = System.currentTimeMillis();
		long l = RedisCacheService.EXPIRED * 1000l; //因为上面的是毫秒，所以这里要* 1000
		switch (RedisCacheService.TIMEUNIT.name()) {
		case "SECONDS":
			availableTime += l;
			break;
		case "MINUTES":
			availableTime += l*60;
			break;
		case "HOURS":
			availableTime += l*60*60;
			break;
		case "DAYS":
			availableTime += l*60*60*24;
			break;
		}
		String token =UUID.randomUUID().toString();
		info.setToken(token);
		info.setAvailableDate(availableTime);

		saveForString(key, info, expired, timeUnit);
	}

	@Override
	public SessionInfo getSessionInfo(String key) {
		Object obj = redisTemplate.opsForValue().get(key);
		if (obj == null) {
			return null;
		}
		System.out.println(obj.getClass().getTypeName());
		return JSONObject.parseObject(obj.toString(), SessionInfo.class);
	}

	@Override
	public boolean checkSeesionExists(String key) {
		
		return false;
	}

	@Override
	public void removeSession(String key) {
		
	}
	
	
	



	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public StringRedisTemplate getStringRedisTemplate() {
		return stringRedisTemplate;
	}

	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	} 

	public HashOperations<String,String,Object> hop(){
		return redisTemplate.opsForHash();
	}
	
	

}
