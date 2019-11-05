package com.oo.businessplan.target.mapper;

import org.apache.ibatis.annotations.Param;

import com.oo.businessplan.basic.mapper.BaseMapper;
import com.oo.businessplan.target.pojo.entity.Target;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-10-11 08:57:13
 */
public interface TargetMapper extends BaseMapper<Target> {
	
	/**
	 * 修改目标的状态
	 * @param id
	 * @param state
	 * @return
	 */
	public int updateState(@Param("id")int id, @Param("state")byte state);
}