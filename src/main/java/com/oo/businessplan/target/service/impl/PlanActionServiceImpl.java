package com.oo.businessplan.target.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.target.mapper.PlanActionMapper;
import com.oo.businessplan.target.service.PlanActionService;
import com.oo.businessplan.target.service.TargetPlanService;
import com.oo.businessplan.target.pojo.entity.PlanAction;
import com.oo.businessplan.target.pojo.entity.TargetPlan;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-12-04 10:49:25
 */
@Service("planActionService")
public class PlanActionServiceImpl extends BaseServiceImpl<PlanAction> implements PlanActionService {
	
	@Autowired
	private TargetPlanService targetPlanService;

    @Autowired
    private PlanActionMapper planActionMapper;

	@Override
	public PlanAction getLaststActionToday(Integer planId, Integer creator) {
		
		if (creator != null) {
			TargetPlan plan = new TargetPlan();
			plan.setId(planId);
			plan.setCreator(creator);
			plan = targetPlanService.getById(plan);
			if (plan == null) {
				return null;
			}
		}
				
		return planActionMapper.getLaststActionToday(planId);
	}

	@Override
	public void deleteTodayAction() {
	    planActionMapper.deleteTodayAction();
	}

	@Override
	public void batchSaveAction(List<PlanAction> actions) {
	    planActionMapper.batchAdd(actions);
	}

	@Override
	public List<PlanAction> getActionLastDay(Integer targetPlanId, Byte... results) {
		
		String resStr = null;
		
		if (results != null) {
			resStr = String.valueOf(results[0]);
			for(int i=1;i<results.length;i++) {
				resStr += "," + results[i];
			}
		}

		List<PlanAction> list = planActionMapper.getActionLastDay(null, resStr);
		
		return list;
	}

	@Override
	public void batchUpdateResult(List<PlanAction> list) {
		planActionMapper.batchUpdateResult(list);
	}
    
    
}