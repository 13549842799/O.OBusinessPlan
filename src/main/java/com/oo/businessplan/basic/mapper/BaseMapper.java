package com.oo.businessplan.basic.mapper;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 这个接口是所有mapper接口的公共接口，包含了一下的几种方法：
 * add方法：插入一套记录
 * delete方法:把一条记录设置为已删除状态
 * update方法:更改某条记录的字段值
 * getById方法:通过id获取某条记录
 * getList方法:获取符合字段条件的list集合
 * @author cyz
 *
 * @param <T>
 */
public interface BaseMapper<T> {
	
	int add(T t) throws UnsupportedEncodingException, NoSuchAlgorithmException;
	
	int delete(T t);
	
	/**
	 * 变量更新
	 * @param t
	 * @return
	 */
    int update(T t);
    
    /**
     * 全量更新
     * @param t
     * @return
     */
    int updateFull(T t);
    
    /**
     * 通过id获取目标对象
     * @param t
     * @return
     */
    T getById(T t);
    
    List<T> getList(T t);

    /**
     * 更新当前状态值为相反的状态,不一定需要记录当前操作账号
     * @param id
     * @param modifierId
     * @return
     */
    public int state(@Param("key")int id,@Param("modifierId")Integer modifierId
    		,@Param("on")byte on,@Param("off")byte off);
}
