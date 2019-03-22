package com.oo.businessplan.common.redis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.validation.constraints.Null;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson.JSONObject;
import com.oo.businessplan.basic.service.BaseService;
import com.oo.businessplan.basic.service.RedisCacheService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.constant.EntityConstants;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.net.SessionInfo;
import com.oo.businessplan.common.security.TokenManager;



public class RedisTokenManager implements TokenManager{
	
	private Logger logger = LoggerFactory.getLogger(RedisTokenManager.class);
	
	//超时时间	
	private long[] expireds;
			
	//时间粒度
	private TimeUnit[] timeUnits;
		
	private StringRedisTemplate stringRedisTemplate;
	
	private RedisTemplate<String,Object> redisTemplate;
	
	@Resource(name="redisTemplate")
	private HashOperations<String,String,Object> hop;
	
	/**
	 * 以字符串的形式保存进redis，设置默认的超时时间和时间粒度
	 * @param key
	 * @param value
	 */
	public void saveForString(String key,Object value){		
		saveForString(key, value, 0, 0);
	}
	
	/**
	 * 以字符串的形式保存进redis，设置对应的超时时间和时间粒度
	 * @param key
	 * @param value
	 * @param expired 超时时间数组的下标
	 * @param timeUnit 时间粒度数组的下标
	 */
	public void saveForString(String key,Object value,int expired,int timeUnit){
		String strValue =parseToStr(value);
		redisTemplate.opsForValue().set(key, strValue,this.expireds[expired],this.timeUnits[timeUnit]);
	}
	
	public void saveForMap(String key,String Hkey,Object value,int expired,int timeUnit){
		//String valueStr =parseToStr(value);
		
		hop.put(key, Hkey, value);
        redisTemplate.expire(key, expireds[expired], timeUnits[timeUnit]);
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
	public Object getValueFromMap(String key,String hashKey,int expired,int timeUnit){		
		Object value =  hop.get(key, hashKey);
		if(value!=null){
	       redisTemplate.expire(key, expireds[expired], timeUnits[timeUnit]);
		}	    
	    return value;
	}
	
	public Object getValueFromMap(String key,String hashKey){
		  return getValueFromMap(key, hashKey, 0, 0);
	}
	
	/**
	 * 更新key为key的默认过期时间
	 */
	public void expire(String key){
		expire(key, 0, 0);
	}
	
	public void expire(String key,int expired,int timeUnit){
		redisTemplate.expire(key,this.expireds[expired],this.timeUnits[timeUnit]);
	}
	
	public void cancel(String key){
		 redisTemplate.delete(key);
	}

	@Override
	public String createToken(SessionInfo sessionInfo) {
		
		String token =UUID.randomUUID().toString();
		sessionInfo.setToken(token);
		
		Map<String,Object> map = new HashMap<>();
		map.put("session", sessionInfo);
		
		saveForMap(sessionInfo.getName(),EntityConstants.REDIS_SESSION_NAME
				, sessionInfo,RedisCacheService.EXPIRED,RedisCacheService.TIMEUNIT);
		
		return token;
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
	
	
	
	@Override
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
	
	
	

	@Override
	public void cancelToken(String key) {
		 cancel(key);
	}



	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public long[] getExpireds() {
		return expireds;
	}

	public void setExpireds(long[] expireds) {
		this.expireds = expireds;
	}

	public TimeUnit[] getTimeUnits() {
		return timeUnits;
	}

	public void setTimeUnits(TimeUnit[] timeUnits) {
		this.timeUnits = timeUnits;
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
