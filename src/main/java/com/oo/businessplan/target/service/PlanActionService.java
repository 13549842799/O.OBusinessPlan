package com.oo.businessplan.target.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.oo.businessplan.basic.service.BaseService;
import com.oo.businessplan.target.pojo.entity.PlanAction;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.pojo.form.PlanActionForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-12-04 10:49:25
 */
public interface PlanActionService extends BaseService<PlanAction> {
	
	/**
	 * 获取对应计划在今天将要执行的动作
	 * @param plan
	 * @return
	 */
	public PlanAction getLaststActionToday(Integer planId, Integer creator);
	
	/**
	 * 删除今天的所有动作
	 */
	public void deleteTodayAction();
	
	/**
	 * 批量保存动作
	 * @param actions
	 */
	public void batchSaveAction(List<PlanAction> actions);
	
	/**
	 * 
	 * @param list
	 */
	public void batchUpdateResult(List<PlanAction> list);
	
	/**
	 * 获取昨日某种状态的action
	 * @param targetPlanId
	 * @param results
	 * @return
	 */
	public List<PlanAction> getActionLastDay(Integer targetPlanId, Byte ...results);

	public PageInfo<PlanActionForm> getPage(PlanAction action, int pageNum, int pageSize);

	/**
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> staticActions(Map<String, Object> params);

	
}