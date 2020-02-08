package com.oo.businessplan.target.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.target.mapper.PlanActionMapper;
import com.oo.businessplan.target.service.PlanActionService;
import com.oo.businessplan.target.service.TargetPlanService;
import com.oo.businessplan.target.service.TargetService;
import com.oo.businessplan.target.pojo.entity.PlanAction;
import com.oo.businessplan.target.pojo.entity.Target;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.pojo.form.PlanActionForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-12-04 10:49:25
 */
@Service("planActionService")
public class PlanActionServiceImpl extends BaseServiceImpl<PlanAction> implements PlanActionService {
	
	@Autowired
	private TargetService targetService;
	
	@Autowired
	private TargetPlanService targetPlanService;

    @Autowired
    private PlanActionMapper planActionMapper;
    
    

	@Override
	public PageInfo<PlanActionForm> getPage(PlanAction action, int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		List<PlanActionForm> list = planActionMapper.getActions(action);
		PageInfo<PlanActionForm> page = new PageInfo<>(list);
		
		return page;
	}

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
	
	public long getTotalCountFromPlan(TargetPlan plan) {
		LocalDate startDate =  plan.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
         endDate = plan.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		switch (plan.getUnit()) {
		case TargetPlan.DAY:
			return ChronoUnit.DAYS.between(startDate, endDate);
        case TargetPlan.WEEK:
        	long dura = ChronoUnit.WEEKS.between(startDate, endDate);
        	return (startDate.getDayOfWeek().getValue() > endDate.getDayOfWeek().getValue() ? dura + 1 : dura) / plan.getPeriod();
		default:
			long d2 = ChronoUnit.MONTHS.between(startDate, endDate);
			return (startDate.getDayOfMonth() > endDate.getDayOfMonth() ? d2 + 1 : d2) / plan.getPeriod();
		}
	}
	
	private Map<String, BigDecimal> getDoneDoData(Integer targetPlanId, int hasDoCount) {
		
		Map<String, BigDecimal> result = new HashMap<>();
		
		TargetPlan plan = new TargetPlan(DeleteFlag.VALID.getCode());
		plan = targetPlanService.getById(plan);
		Target target = new Target(plan.getTargetId(), DeleteFlag.VALID.getCode());
		target = targetService.getById(target);
		//计算单次耗时多少秒
		long consumeSecond = plan.getEndTime().getTime() - plan.getExecutionTime().getTime();
		
		long count = 0, totalCount = 0;
        LocalDate now = LocalDate.now();
        LocalDate startDate =  plan.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = plan.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		switch (plan.getUnit()) {
		case TargetPlan.DAY:
			count = (ChronoUnit.DAYS.between(startDate, endDate) - hasDoCount) / plan.getPeriod();
			break;
        case TargetPlan.WEEK:
        	long dura = ChronoUnit.WEEKS.between(startDate, endDate);
        	dura = startDate.getDayOfWeek().getValue() > endDate.getDayOfWeek().getValue() ? dura + 1 : dura;
        	count = dura / plan.getPeriod();
			break;
		default:
			count = ChronoUnit.MONTHS.between(startDate, endDate);
			count = startDate.getDayOfMonth() > endDate.getDayOfMonth() ? count + 1 : count;
			count = count / plan.getPeriod();
		}
		
		return result;
	}

	@Override
	public void batchUpdateResult(List<PlanAction> list) {
		planActionMapper.batchUpdateResult(list);
	}

	@Override
	public Map<String, Object> staticActions(Map<String, Object> params) {
		
		
		int planId = Integer.parseInt(params.get("planId").toString());
		
		Map<String, Integer> hasDo = planActionMapper.getActionsStatic(planId);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("hasDo", hasDo);
		
		//Map<String, BigDecimal> doneDo = getDoneDoData(planId);
		//result.put("doneDo", doneDo);
		
		return result;
	}
    
    
}