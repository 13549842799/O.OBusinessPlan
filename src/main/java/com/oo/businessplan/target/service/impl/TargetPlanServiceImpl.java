package com.oo.businessplan.target.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.target.mapper.TargetPlanMapper;
import com.oo.businessplan.target.service.TargetPlanService;
import com.oo.businessplan.target.pojo.entity.TargetPlan;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-10-24 16:38:41
 */
@Service("targetPlanService")
public class TargetPlanServiceImpl extends BaseServiceImpl<TargetPlan> implements TargetPlanService {

    @Autowired
    TargetPlanMapper targetPlanMapper;
}