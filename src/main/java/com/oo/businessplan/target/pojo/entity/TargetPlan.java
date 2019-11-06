package com.oo.businessplan.target.pojo.entity;

import java.sql.Timestamp;
import java.util.List;

import com.oo.businessplan.basic.entity.DeleteAbleEntity;

public class TargetPlan extends DeleteAbleEntity<Integer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4477529079701235809L;
	
	/**
	 * 计划名称
	 */
	private String planName;

	/**
	 * 计划内容
	 */
	private String content;
	
	/**
	 * 执行时间,小计划开始执行时间
	 * 保存前需要校验是否存在重叠时间的计划执行，如果有则提醒。
	 */
	private Timestamp executionTime;
	
	/**
	 * 预期结束时间
	 */
	private Timestamp endTime;
	
	/**
	 * 周期 
	 */
	private Integer period;
	
	/**
	 * 单位 1-小时  2-天 3-周  4-月
	 */
	private Byte unit;
	
	/**
	 * 实际执行的结果
	 */
	private List<PlanAction> actions;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Timestamp executionTime) {
		this.executionTime = executionTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Byte getUnit() {
		return unit;
	}

	public void setUnit(Byte unit) {
		this.unit = unit;
	}

	public List<PlanAction> getActions() {
		return actions;
	}

	public void setActions(List<PlanAction> actions) {
		this.actions = actions;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	
	

}
