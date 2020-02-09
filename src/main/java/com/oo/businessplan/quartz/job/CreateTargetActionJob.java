package com.oo.businessplan.quartz.job;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.oo.businessplan.target.pojo.entity.PlanAction;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.service.PlanActionService;
import com.oo.businessplan.target.service.TargetPlanService;

/**
 * 时钟任务类-定时生成目标执行动作
 * 在每日凌晨1点时，查询系统中当天所有将要执行的任务，并生成动作记录
 * @author cyz
 *
 */
public class CreateTargetActionJob extends QuartzJobBean {
	
	private TargetPlanService targetPlanService;
	
	private PlanActionService planActionService;

	public TargetPlanService getTargetPlanService() {
		return targetPlanService;
	}

	public void setTargetPlanService(TargetPlanService targetPlanService) {
		this.targetPlanService = targetPlanService;
	}

	public PlanActionService getPlanActionService() {
		return planActionService;
	}

	public void setPlanActionService(PlanActionService planActionService) {
		this.planActionService = planActionService;
	}

	/**
	 * 
	 */
	@Transactional
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("==================开始创建动作 ===================================");
		List<TargetPlan> willPlan = targetPlanService.getWillExecutePlanListInDay(null);
		List<PlanAction> actions = new ArrayList<>();
        try {
        	
    		planActionService.deleteTodayAction();//预防当天生成多个动作
    		
    		PlanAction action = null;
    		long dayMinSecond = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    		Date now = new Date(dayMinSecond);
    		for (TargetPlan plan : willPlan) {
    						
    			action = new PlanAction();
    			action.setTargetPlanId(plan.getId());
    			action.setResult(PlanAction.UNSTART);
    			action.setActionDate(now); 			
    			action.setExpectStartTime(new Timestamp(dayMinSecond + plan.getExecutionTime().getTime()));
    			action.setExpectEndTime(new Timestamp(dayMinSecond + plan.getEndTime().getTime()));
    			action.setNum(plan.countAddOne());
    			actions.add(action);
    		}
    		planActionService.batchSaveAction(actions);
    		
    		targetPlanService.updateCountBatch(willPlan);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
        System.out.println("==================结束创建动作 ===================================");
	}

}
