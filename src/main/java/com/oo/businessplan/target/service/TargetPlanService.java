package com.oo.businessplan.target.service;

import java.util.List;

import com.oo.businessplan.basic.service.BaseService;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.pojo.entity.TargetPlanAlterRecord;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-10-24 16:38:41
 */
public interface TargetPlanService extends BaseService<TargetPlan> {
	
	/**
	 * 获取当天将要执行的计划
	 * @return
	 */
	public List<TargetPlan> getWillExecutePlanListInDay(Integer creator);
	
	/**
	 * 判断计划的执行时间是否存在重叠的区域
	 * @param plan
	 * @return
	 */
	public List<TargetPlan> isOverLappedTime(TargetPlan plan);
	
	/**
	 * 获取修改记录列表
	 * @param id
	 * @param creatorId
	 * @return
	 */
	public List<TargetPlanAlterRecord> recordsList(Integer id, Integer creatorId);
	
	public List<TargetPlan> unCompleteList(int creator);

	/**
	 * 
	 * @param willPlan
	 */
	public void updateCountBatch(List<TargetPlan> willPlan);
	
	/**
	 * 
	 * @param plan
	 * @return
	 */
	public long getTotalCountFromPlan(TargetPlan plan);
	
}