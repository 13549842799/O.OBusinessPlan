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
    public SectionMapper sectionMapper;
    
    @Autowired
    PortionMapper portionMapper;
    
    @Autowired
    NovelMapper novelMapper;
    
    

	@Override
	public SectionForm getExpandSection(Section section) {
		
		return sectionMapper.getExpandSection(section);
	}

	@Override
	public List<Section> getSimpleSections(Section section) {
		// TODO Auto-generated method stub
		return sectionMapper.getSimpleSections(section);
	}

	@Override
	public Long lastSectionId(Section current) {
		if (current == null) {
			return null;
		}
		return sectionMapper.lastSectionId(current);
	}

	@Override
	public Long nextSectionId(Section current) {
		if (current == null) {
			return null;
		}
		return sectionMapper.nextSectionId(current);
	}

	@Transactional
	@Override
	public boolean delete(Section t) {
		if (t == null) {
			return false;
		}
		t = sectionMapper.getById(t);

		Section last = new Section(t.getLastSection());
		last.setNextSection(t.getNextSection());
		
		Section next = new Section(t.getNextSection());
		next.setLastSection(t.getLastSection());
		sectionMapper.update(last);
		sectionMapper.update(next);
		return super.delete(t);
	}

	
	
	
    
}