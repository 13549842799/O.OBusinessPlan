package com.oo.businessplan.target.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.target.mapper.TargetMapper;
import com.oo.businessplan.target.service.TargetService;
import com.oo.businessplan.target.pojo.entity.Target;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-10-11 08:57:13
 */
@Service("targetService")
public class TargetServiceImpl extends BaseServiceImpl<Target> implements TargetService {

    @Autowired
    TargetMapper targetMapper;
}