package com.oo.businessplan.article.mapper;

import com.oo.businessplan.basic.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.oo.businessplan.article.pojo.entity.Label;
import com.oo.businessplan.article.pojo.form.LabelForm;


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
	
	/**
	 * 批量插入或更新label
	 * @param list
	 */
	void insertOrUpdate(List<Label> list);
	
	/**
	 * 查询频繁被使用的标签的名称
	 * @param creator
	 * @param targetType
	 * @return
	 */
	Set<String> frequentlyUsedLabelNames(@Param("creator")Integer creator, @Param("targetType")Byte targetType, @Param("delflag")byte delfalg, @Param("count")int count);
	
	/**
	 * 获取分类的标签
	 * @param form
	 * @return
	 */
	List<Label> getGroupList(Label form);
}