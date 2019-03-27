package com.oo.businessplan.basic.service;

import java.util.List;
import java.util.Map;

import com.oo.businessplan.common.exception.AddErrorException;

public interface BaseService<T> {
	
	/**
	 * 通过id获取对象
	 * @param id
	 * @return
	 */
	T getById(T t);
	
	/**
	 * 通过条件获取对应的对象集合
	 * @param T
	 * @return
	 */
	List<T> getList(T t) ;
	
	/**
	 * 基本对象的通用插入数据方法
	 * @param t
	 */
	void add(T t) throws AddErrorException;
	
	/**
	 * 变量更新
	 * @param t
	 * @return 1-成功更新  0-没有更新  -1-更新多条
	 */
	int update(T t);
	
	/**
	 * 全量更新
	 * @param t
	 * @return 1-成功更新  0-没有更新  -1-更新多条
	 */
	int updateFull(T t);
	
	/**
	 * 把目标的状态(state)更新为相关的值 ,例如 1 => 0,0 => 1
	 * 并且记录当前操作账号
	 * @param id 需要更新状态的记录id
	 * @param modifierId 当前操作的账号的id
	 * @return
	 */
	boolean state(int id,Integer modifierId);
	
	/**
	 * 把目标的状态(state)更新为相关的值 ,例如 1 => 0,0 => 1
	 * @param id
	 * @return
	 */
	boolean state(int id);
	
	/**
	 * 删除记录
	 * @param id
	 * @return
	 */
	boolean delete(T t);

}
