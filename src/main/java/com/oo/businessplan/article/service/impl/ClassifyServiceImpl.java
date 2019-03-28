package com.oo.businessplan.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oo.businessplan.article.mapper.ClassifyMapper;
import com.oo.businessplan.article.pojo.entity.Classify;
import com.oo.businessplan.article.service.ClassifyService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.enumeration.DeleteFlag;

@Service("classifyService")
public class ClassifyServiceImpl extends BaseServiceImpl<Classify> implements ClassifyService {
	
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

	
}
