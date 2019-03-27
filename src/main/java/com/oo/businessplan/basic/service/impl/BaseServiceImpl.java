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
	
	@Autowired
	protected BaseMapper<T> baseMapper;
	
	public T getById(T t) {
		return baseMapper.getById(t);
	}
	
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
	
	public boolean delete(T t) {
		return baseMapper.delete(t) == 1;
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
