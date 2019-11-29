package com.oo.businessplan.target.mapper;

import java.util.List;

import com.oo.businessplan.basic.mapper.BaseMapper;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.pojo.form.TargetPlanForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-10-24 16:38:41
 */
public interface TargetPlanMapper extends BaseMapper<TargetPlan> {
	
	public List<TargetPlan> getListByTarget(TargetPlanForm form);
}