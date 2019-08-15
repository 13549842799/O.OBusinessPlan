package com.oo.businessplan.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.mapper.NovelMapper;
import com.oo.businessplan.article.service.NovelService;
import com.oo.businessplan.article.pojo.entity.Novel;
import com.oo.businessplan.article.pojo.form.NovelForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-03-28 15:10:47
 */
@Service("novelService")
public class NovelServiceImpl extends BaseServiceImpl<Novel> implements NovelService {

    @Autowired
    NovelMapper novelMapper;

	@Override
	public PageInfo<NovelForm> getPage(NovelForm form) {
		
		PageHelper.startPage(form.getPageNum(), form.getPageSize());
		List<NovelForm> list = novelMapper.getExpandList(form);
		PageInfo<NovelForm> page = new PageInfo<>(list);		
		return page;
	}

    
}