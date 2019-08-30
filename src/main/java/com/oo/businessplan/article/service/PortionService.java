package com.oo.businessplan.article.service;

import com.oo.businessplan.basic.service.BaseService;

import java.util.List;

import com.oo.businessplan.article.pojo.entity.Portion;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-20 10:21:41
 */
public interface PortionService extends BaseService<Portion> {
	
	List<Portion> getExpandList(Portion portion);
}