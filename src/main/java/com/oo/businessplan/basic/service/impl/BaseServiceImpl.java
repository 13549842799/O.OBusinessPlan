package com.oo.businessplan.basic.service.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.oo.businessplan.basic.mapper.BaseMapper;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.enumeration.StatusFlag;
import com.oo.businessplan.common.exception.AddErrorException;
import com.oo.businessplan.common.exception.AddErrorException.ErrorType;
import com.oo.businessplan.common.redis.RedisTokenManager;

public abstract class BaseServiceImpl<T> {
	
	  
	/*@Autowired
    protected RedisTokenManager tokenManager;*/
	
	@Autowired
	protected BaseMapper<T> baseMapper;
	
	/**
	 * 从redis中获取key为key，hashkey为hashkey的对象，如果没有，则从数据库中获取
	 * 符合要求，符合状态的记录，然后保存在redis中
	 * 在redis中
	 * @param mapper
	 * @param key
	 * @param hashKey
	 * @param state
	 * @param expired
	 * @param timeUnit
	 * @return
	 */
	/*private  T getObject(BaseMapper<T> mapper,String key,String hashKey,Byte state,int expired,int timeUnit){
		 
		@SuppressWarnings("unchecked")
		T t =(T)tokenManager.getValueFromMap(key, hashKey, expired, timeUnit);
		if (t==null) {
			t = mapper.getByStr(key,DeleteFlag.VALID.getCode(), state);
			tokenManager.saveForMap(key, hashKey, t, expired, timeUnit);
		}
		
	    return t;
	}*/
	
	/**
	 * 与上面的方法类似，不过在从数据库中获取记录时不需加入状态条件
	 * @param mapper
	 * @param key
	 * @param hashKey
	 * @param expired
	 * @param timeUnit
	 * @return
	 */
	/*public T getObject(BaseMapper<T> mapper,String key,String hashKey,int expired,int timeUnit){
		T t =getObject(mapper, key, hashKey, null, expired, timeUnit);
	    return t;
	}*/
	
	public T getById(T t) {
		return baseMapper.getById(t);
	}
	
	/**
	 * 
	 * @param mapper
	 * @param key
	 * @param hashKey
	 * @param state
	 * @param expired
	 * @param timeUnit
	 * @return
	 */
	/*private  List<T> getListObject(BaseMapper<T> mapper,String key,String hashKey,Byte state,int expired,int timeUnit){
		 
		@SuppressWarnings("unchecked")
		List<T> t =(List<T>)tokenManager.getValueFromMap(key, hashKey, expired, timeUnit);
		if (t==null) {
			t = mapper.getListByStr(key, DeleteFlag.VALID.getCode(), state);
			tokenManager.saveForMap(key, hashKey, t, expired, timeUnit);
		}
		
	    return t;
	}*/
	
	/**
	 * 
	 * @param mapper
	 * @param key
	 * @param hashKey
	 * @param expired
	 * @param timeUnit
	 * @return
	 */
	/*public List<T> getListObject(BaseMapper<T> mapper,String key,String hashKey,int expired,int timeUnit){
		 
	    return getListObject(mapper, key, hashKey,null, expired, timeUnit);
	}*/
	
	/**
	 * 移除保存在redis中键为key的对象
	 * @param key
	 * @return
	 */
	/*public boolean remove(String key){
		try {
			tokenManager.cancel(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
	/**
	 * 通用方法，获取某个对象的list集合
	 * @param t
	 * @return
	 */
	public List<T> getList(T t) {
		return baseMapper.getList(t);
	}
	
	public void add(T t) throws AddErrorException {
		fullDeleteSign(t);
	    try {
		  	int count = baseMapper.add(t);
		  	if (count == 0) {
		  		throw new AddErrorException(ErrorType.OBJECT_NOT_ALL);
		  	}
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new AddErrorException(ErrorType.OBJECT_FUNCTION_ERROR);
		}
	}
	
	@Transactional
	public int update(T t) {
		fullDeleteSign(t);
		int count = baseMapper.update(t);
		if (count == 0 || count == 1) {
			return count;
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return -1;
	}
	
	@Transactional
	public int updateFull(T t) {
		fullDeleteSign(t);
		int count = baseMapper.updateFull(t);
		if (count == 0 || count == 1) {
			return count;
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return -1;
	}
	
	public boolean state(int id) {
		return state(id, null);
	}
	
	public boolean state(int id, Integer modifierId) {		
		return !(baseMapper.state(id, modifierId
				,StatusFlag.ENABLE.getCode(),StatusFlag.DISABLE.getCode()) == 0);
	}
	
	public boolean delete(int id) {
		return baseMapper.delete(DeleteFlag.DELETE.getCode(), id) == 1;
	}
	
	/**
	 * 为对象补充值为正常的删除标志属性
	 * @param t
	 */
	private void fullDeleteSign (T t) {
		Class<? extends Object> cls = t.getClass();
		try {
			Method method = cls.getMethod("setDelflag", Byte.class);
			if (method != null) {
				method.invoke(t, DeleteFlag.VALID.getCode());
			}				
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} 
	}

}
