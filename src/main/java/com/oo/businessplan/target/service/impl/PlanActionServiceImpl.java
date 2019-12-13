package com.oo.businessplan.target.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.target.mapper.PlanActionMapper;
import com.oo.businessplan.target.service.PlanActionService;
import com.oo.businessplan.target.pojo.entity.PlanAction;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-12-04 10:49:25
 */
@Service("planActionService")
public class PlanActionServiceImpl extends BaseServiceImpl<PlanAction> implements PlanActionService {

    @Autowired
    PlanActionMapper planActionMapper;
}