package com.oo.businessplan.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.article.mapper.SectionMapper;
import com.oo.businessplan.article.service.SectionService;
import com.oo.businessplan.article.pojo.entity.Section;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-03-28 15:25:34
 */
@Service("sectionService")
public class SectionServiceImpl extends BaseServiceImpl<Section> implements SectionService {

    @Autowired
    SectionMapper sectionMapper;
}