package com.oo.businessplan.common.redis;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson.JSONObject;

public class RedisManager {
	
	
	//超时时间	
	private long[] expireds;
		
	//时间粒度
	private TimeUnit[] timeUnits;
	
	private StringRedisTemplate stringRedisTemplate;
	
	private ValueOperations<String,String> vop ;
	
	private HashOperations<String,String,Object> hop ;
	
	/**
	 * 保存键值对在redis中
	 * @param key
	 * @param value
	 * @param expired
	 * @param timeUnit
	 */
	public void save(String key,Map<String,Object> value){
		      
		String valueStr = JSONObject.toJSONString(value);
		
		vop.set(key,valueStr,this.expireds[0],this.timeUnits[0]);
	}
	
	public <T> T get(Class<T> cls,String key,String expired,String timeUnit){
		
		String valueStr = vop.get(key);
		return Objects.isNull(valueStr)?null:JSONObject.parseObject(valueStr,cls);
	}
	
	public JSONObject getJsonObject(String key){
		String valueStr = vop.get(key);
		return Objects.isNull(valueStr)?null:JSONObject.parseObject(valueStr);
	}
	
	public void expire(String key){
		stringRedisTemplate.expire(key,this.expireds[0],this.timeUnits[0]);
	}
	

	public boolean isAlive(String key){
		return vop.get(key)!=null;
	}
	public void cancel(String key){
		 stringRedisTemplate.delete(key);
	}

	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	public void setVop(ValueOperations<String, String> vop) {
		this.vop = vop;
	}

	public HashOperations<String, String, Object> getHop() {
		return hop;
	}

	public void setHop(HashOperations<String, String, Object> hop) {
		this.hop = hop;
	}

	public ValueOperations<String, String> getVop() {
		return vop;
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
	
	
	
	
	

}
