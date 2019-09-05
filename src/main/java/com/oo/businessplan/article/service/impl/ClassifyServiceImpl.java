package com.oo.businessplan.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.mapper.ClassifyMapper;
import com.oo.businessplan.article.pojo.entity.Classify;
import com.oo.businessplan.article.service.ClassifyService;
import com.oo.businessplan.basic.service.PageService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.enumeration.DeleteFlag;

@Service("classifyService")
public class ClassifyServiceImpl extends BaseServiceImpl<Classify> implements ClassifyService, PageService<Classify> {
	
	@Autowired
	private ClassifyMapper classifyMapper;

	@Override
	public int articleCountOfClassify(Classify cls) {
		
		return classifyMapper.checkTheClassifyArticleCount(cls.getThisTarget(), cls.getCreator(), cls.getId(), DeleteFlag.VALID.getCode());
	}



	@Override
	public void moveArticleFromOldClassify(Classify cls, int newClassifyId) {
		
		classifyMapper.moveArticle(cls.getThisTarget(), cls.getCreator(), cls.getId(), newClassifyId);
	}



	/*@Override
	public PageInfo<Classify> getPage(Classify cls, Integer pageNum, Integer pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		List<Classify> list = classifyMapper.getList(cls);
		PageInfo<Classify> page = new PageInfo<>(list);
		return page;

	}*/



	@Override
	public Classify getUnique(Classify cls) {
		
		return classifyMapper.getUnique(cls);
	}



	@Override
	public boolean checkExists(String name, int creator, byte childType) {
		
		return classifyMapper.count(name, Classify.SYSTEMCLASSIFY, childType, creator, DeleteFlag.VALID.getCode()) > 0;
	}

	
	
}
