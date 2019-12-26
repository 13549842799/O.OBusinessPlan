package com.oo.businessplan.article.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oo.businessplan.basic.entity.CreatorEntity;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.constant.ArticleConstant.ArticleType;
import com.oo.businessplan.common.enumeration.DeleteFlag;
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
		//List<Label> labels = labelMapper.getList(label);
		List<Label> labels = labelMapper.getGroupList(label);
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

	@Override
	public void batchAddOrUpdate(List<Label> labels, CreatorEntity<Integer> target, byte targetType) {
		
		if (labels == null || labels.size() == 0) {
			return;
		}
		Timestamp now = new Timestamp(new Date().getTime());
		for (Label l : labels) {
			l.setCreator(target.getCreator());
			l.setCreateTime(now);
			l.setTargetType(targetType);
			l.setTargetId(target.getId());
		}
		labelMapper.insertOrUpdate(labels);
	}

	@Override
	public Set<String> frequentlyUsedLabelNames(Label label) {
		
		Set<String> nameForSelf = labelMapper.frequentlyUsedLabelNames(label.getAdminId(), label.getTargetType(), DeleteFlag.VALID.getCode(), 6);
		
		nameForSelf = nameForSelf != null ? nameForSelf : new HashSet<>();
		
		Set<String> nameForAll = labelMapper.frequentlyUsedLabelNames(label.getAdminId(), label.getTargetType(), DeleteFlag.VALID.getCode(), 6);
		nameForAll = nameForAll != null ? nameForAll : new HashSet<>();
		
		nameForSelf.addAll(nameForAll);
		return nameForSelf;
	}
    
	
    
}