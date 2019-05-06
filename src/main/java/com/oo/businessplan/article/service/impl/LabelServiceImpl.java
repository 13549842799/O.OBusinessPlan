package com.oo.businessplan.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.mapper.LabelMapper;
import com.oo.businessplan.article.service.LabelService;
import com.oo.businessplan.article.pojo.entity.Label;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-04-19 15:55:26
 */
@Service("labelService")
public class LabelServiceImpl extends BaseServiceImpl<Label> implements LabelService {

    @Autowired
    LabelMapper labelMapper;

	@Override
	public PageInfo<Label> page(Label label, Integer pageNum, Integer pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		List<Label> labels = labelMapper.getList(label);
		PageInfo<Label> page = new PageInfo<>(labels);	
		return page;
	}

	@Override
	public boolean checkNameExists(Integer adminId, String name) {
		
		return labelMapper.checkNameExists(adminId, name) > 0;
	}

	@Override
	public int hasUseCount(int id) {

		return 0;
	}
    
    
}