package com.oo.businessplan.quartz.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.oo.businessplan.target.pojo.entity.PlanAction;
import com.oo.businessplan.target.service.PlanActionService;

/**
 * 在第二天时更新昨天的所有没有执行和正在执行的动作的结果，分别将它们改为放弃和没有完成
 * @author cyz
 *
 */
public class UpdateActionResultJob extends QuartzJobBean{
	
	PlanActionService planActionService;

	public PlanActionService getPlanActionService() {
		return planActionService;
	}

	public void setPlanActionService(PlanActionService planActionService) {
		this.planActionService = planActionService;
	}

	@Transactional
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("==================动作结果更新开始===================================");
		List<PlanAction> actions = null;
		try {
			actions = planActionService.getActionLastDay(null, PlanAction.WAITSTART, PlanAction.UNSTART, PlanAction.ACTIONING);
			if (actions != null && actions.size() > 0) {
				for (PlanAction action : actions) {
					if (action.getStartTime() == null) {
						action.setResult(PlanAction.GIVEUP);
					} else if (action.getEndTime() == null) {
						action.setResult(PlanAction.UNCOMPLETE);
					}
				}
				planActionService.batchUpdateResult(actions);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		System.out.println("==================动作更新结束 总共更新："+ actions.size() +"条  ===================================");
	}

}
