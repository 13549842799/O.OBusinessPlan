package com.oo.businessplan.target.pojo.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import com.oo.businessplan.basic.entity.IdEntity;

/**
 * 目标计划修改记录
 * @author cyz
 *
 */
public class TargetPlanAlterRecord extends IdEntity<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7696983624058745597L;
	
	public TargetPlanAlterRecord() {}
	
	public TargetPlanAlterRecord(TargetPlan plan) {
		this.planName = plan.getPlanName();
		this.content = plan.getContent();
		this.executionTime = plan.getExecutionTime();
		this.endTime = plan.getEndTime();
		this.period = plan.getPeriod();
		this.unit = plan.getUnit();
		this.startDate = plan.getStartDate();
		this.createTime = new Timestamp(new Date().getTime());
		this.reason = plan.getDeleteReason();
		this.targetPlanId = plan.getId();
	}

	private String planName;

	/**
	 * 计划内容
	 */
	private String content;
	
	/**
	 * 执行时间,小计划开始执行时间
	 * 保存前需要校验是否存在重叠时间的计划执行，如果有则提醒。
	 */
	private Time executionTime;
	
	/**
	 * 预期结束时间
	 */
	private Time endTime;
	
	/**
	 * 周期 
	 */
	private Integer period;
	
	/**
	 * 单位 1-小时（弃用）  2-天 3-周  4-月
	 */
	private Byte unit;
	
	/**
	 * 计划第一次开始的日期
	 */
	private Date startDate;
	
	private Timestamp createTime;
	
	/**
	 * 理由
	 */
	private String reason;
	
	private Integer targetPlanId;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public Time getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Time executionTime) {
		this.executionTime = executionTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getTargetPlanId() {
		return targetPlanId;
	}

	public void setTargetPlanId(Integer targetPlanId) {
		this.targetPlanId = targetPlanId;
	}
	
	

}
