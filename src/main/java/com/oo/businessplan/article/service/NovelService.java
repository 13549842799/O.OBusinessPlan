package com.oo.businessplan.article.service;

import com.oo.businessplan.basic.service.BaseService;

import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.pojo.entity.Novel;
import com.oo.businessplan.article.pojo.form.NovelForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-03-28 15:08:28
 */
public interface NovelService extends BaseService<Novel> {
	
	PageInfo<NovelForm> getPage(NovelForm form);
}