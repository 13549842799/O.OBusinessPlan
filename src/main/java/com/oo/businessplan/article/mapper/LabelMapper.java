package com.oo.businessplan.article.mapper;

import com.oo.businessplan.basic.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import com.oo.businessplan.article.pojo.entity.Label;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-04-19 15:55:26
 */
public interface LabelMapper extends BaseMapper<Label> {

	/**
	 * 检测是否存在name
	 * @param adminId
	 * @param name
	 * @return
	 */
	int checkNameExists(@Param("adminId")Integer adminId, @Param("name")String name);
}