package com.oo.businessplan.target.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.basic.mapper.BaseMapper;
import com.oo.businessplan.target.pojo.entity.PlanAction;
import com.oo.businessplan.target.pojo.form.PlanActionForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-12-04 10:49:25
 */
public interface PlanActionMapper extends BaseMapper<PlanAction> {
	
	/**
	 * 获取对应计划最新的动作对象
	 * @param planId
	 * @return
	 */
	@Select("select * from plan_action where targetPlanId = #{targetPlanId} and TO_DAYS(actionDate) = TO_DAYS(now()) limit 1")
	PlanAction getLaststActionToday(@Param("targetPlanId")Integer planId);
	
	/**
	 * 获取昨天的动作
	 * @param planId
	 * @return
	 */
	List<PlanAction> getActionLastDay(@Param("targetPlanId")Integer planId, @Param("results")String results);
	
	/**
	 * 批量插入空白计划动作，插入的字段包括(result,targetPlanId,actionDate)
	 * @param list
	 */
	void batchAdd(List<PlanAction> list);
	
	/**
	 * 批量更新动作的结果
	 * @param list
	 */
	void batchUpdateResult(List<PlanAction> list);
	
	@Delete("delete from plan_action where TO_DAYS(actionDate) = TO_DAYS(now())")
	void deleteTodayAction();
	
	List<PlanActionForm> getActions(PlanAction action);
	
	@Select("select  ifnull(sum(if(result = 3, 1, 0)), 0) as a, IFNULL(sum(if(result = 4, 1, 0)), 0) as b, ifnull(sum(if(result = 5, 1, 0)), 0) as c, ifnull(sum(if(result = 6, 1, 0)), 0) as d, ifnull(count(0), 0) as e from plan_action where targetPlanId = #{planId} AND result in (3,4,5,6) ")
	Map<String, Long> getActionsStatic(@Param("planId")int planId);
}