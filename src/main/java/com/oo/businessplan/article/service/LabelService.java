package com.oo.businessplan.article.service;

import com.oo.businessplan.basic.service.BaseService;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.pojo.entity.Label;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-04-19 15:55:26
 */
public interface LabelService extends BaseService<Label> {

	/**
	 * 获取分页的标签列表
	 * @param label
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Label> page(Label label, Integer pageNum, Integer pageSize);
	
	/**
	 * 检查是否存在对应的名字
	 * @param adminId
	 * @param name
	 * @return
	 */
	boolean checkNameExists(Integer adminId, String name);

	/**
	 * 查询还有多少篇文章在使用当前的标签
	 * @param id
	 * @return
	 */
	int hasUseCount(int id);
}