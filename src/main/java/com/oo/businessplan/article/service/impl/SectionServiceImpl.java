package com.oo.businessplan.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.exception.AddErrorException;
import com.oo.businessplan.article.mapper.NovelMapper;
import com.oo.businessplan.article.mapper.PortionMapper;
import com.oo.businessplan.article.mapper.SectionMapper;
import com.oo.businessplan.article.service.SectionService;
import com.oo.businessplan.article.pojo.entity.Section;
import com.oo.businessplan.article.pojo.form.SectionForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-20 15:55:56
 */
@Service("sectionService")
public class SectionServiceImpl extends BaseServiceImpl<Section> implements SectionService {

    @Autowired
    SectionMapper sectionMapper;
    
    @Autowired
    PortionMapper portionMapper;
    
    @Autowired
    NovelMapper novelMapper;
    
    

	@Override
	public SectionForm getExpandSection(Section section) {
		
		return sectionMapper.getExpandSection(section);
	}



	@Override
	@Transactional
	public boolean delete(Section t) {
		t = sectionMapper.getById(t);
		if (t == null) {
			System.out.println("delete:不存在对应的章节:id");
			return false;
		}
		System.out.println("进入维护序列");
		try {
			sectionMapper.addToDel(t);
			if (super.delete(t)) {
				return true;
			}
			throw new Exception();
		} catch (Exception e) {
			System.out.println("删除失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}



	@Override
	public List<Section> getSimpleSections(Section section) {
		// TODO Auto-generated method stub
		return sectionMapper.getSimpleSections(section);
	}
    
	
	
    
}