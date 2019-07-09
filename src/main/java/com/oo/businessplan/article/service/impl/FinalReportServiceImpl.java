package com.oo.businessplan.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oo.businessplan.article.mapper.ArticleMapper;
import com.oo.businessplan.article.mapper.FinalReportMapper;
import com.oo.businessplan.article.pojo.entity.FinalReport;
import com.oo.businessplan.article.service.FinalReportService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;

@Service
public class FinalReportServiceImpl extends BaseServiceImpl<FinalReport> implements FinalReportService {
	
	@Autowired
	private FinalReportMapper mapper;

	@Override
	public ArticleMapper<FinalReport> getMapper() {
		return this.mapper;
	}

	

}
