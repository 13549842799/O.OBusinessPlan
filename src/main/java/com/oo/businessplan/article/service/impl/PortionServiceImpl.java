package com.oo.businessplan.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.article.mapper.PortionMapper;
import com.oo.businessplan.article.service.PortionService;
import com.oo.businessplan.article.pojo.entity.Portion;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-03-28 15:24:19
 */
@Service("portionService")
public class PortionServiceImpl extends BaseServiceImpl<Portion> implements PortionService {

    @Autowired
    PortionMapper portionMapper;
}