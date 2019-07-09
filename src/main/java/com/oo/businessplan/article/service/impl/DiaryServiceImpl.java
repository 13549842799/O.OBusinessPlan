package com.oo.businessplan.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.mapper.ArticleMapper;
import com.oo.businessplan.article.mapper.DiaryMapper;
import com.oo.businessplan.article.pojo.entity.Diary;
import com.oo.businessplan.article.pojo.form.DiaryForm;
import com.oo.businessplan.article.service.DiaryService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.enumeration.DeleteFlag;

@Service("diaryService")
public class DiaryServiceImpl extends BaseServiceImpl<Diary> implements DiaryService {
	
	@Autowired
	private DiaryMapper diaryMapper;
	
	

	@Override
	public ArticleMapper<Diary> getMapper() {
		return this.diaryMapper;
	}

	@Override
	public PageInfo<DiaryForm> getPage(DiaryForm form) {
		
		PageHelper.startPage(form.getPageNum(), form.getPageSize());
		List<DiaryForm> diaries = diaryMapper.getListWithLabels(form);
		PageInfo<DiaryForm> page = new PageInfo<>(diaries);
		return page;
	}

	@Override
	public DiaryForm getCompleteDiary(int id) {
		
		DiaryForm diary = diaryMapper.getCompleteDiaryById(id, DeleteFlag.VALID.getCode());
		
		return diary;
	}
	
	

}
