package com.oo.businessplan.article.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.mapper.DiaryMapper;
import com.oo.businessplan.article.pojo.entity.Diary;
import com.oo.businessplan.article.service.DiaryService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;

@Service("diaryService")
public class DiaryServiceImpl extends BaseServiceImpl<Diary> implements DiaryService {
	
	private DiaryMapper diaryMapper;

	@Override
	public PageInfo<Diary> getPage(Integer pageNum, Integer adminId, Integer classify) {
		
		PageHelper.startPage(pageNum, pageSize);
		List<Diary> diaries = diaryMapper.getList(null);
		PageInfo<Diary> page = new PageInfo<>(diaries);
		return page;
	}

}
