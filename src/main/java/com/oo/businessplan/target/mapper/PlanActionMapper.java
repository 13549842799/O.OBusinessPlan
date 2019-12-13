package com.oo.businessplan.target.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.basic.mapper.BaseMapper;
import com.oo.businessplan.target.pojo.entity.PlanAction;


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
	
}