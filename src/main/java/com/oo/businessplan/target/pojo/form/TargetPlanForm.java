package com.oo.businessplan.target.pojo.form;

import com.oo.businessplan.target.pojo.entity.Target;
import com.oo.businessplan.target.pojo.entity.TargetPlan;

/**
 * 
 * @author cyz
 *
 */
public class TargetPlanForm extends TargetPlan{

	private Target target = new Target();

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
	
	
	
}
