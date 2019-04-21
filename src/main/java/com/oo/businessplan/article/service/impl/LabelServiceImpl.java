package com.oo.businessplan.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
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
}