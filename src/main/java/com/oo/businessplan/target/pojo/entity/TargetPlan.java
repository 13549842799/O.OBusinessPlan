package com.oo.businessplan.target.pojo.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oo.businessplan.basic.entity.CreatorEntity;
import com.oo.businessplan.common.valid.FieldMeta;

public class TargetPlan extends CreatorEntity<Integer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4477529079701235809L;
	
	
	
	public TargetPlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TargetPlan(Byte delflag) {
		super(delflag);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 计划名称
	 */
	@FieldMeta("计划名称")
	private String planName;

	/**
	 * 计划内容
	 */
	@FieldMeta(value="计划内容", max="200")
	private String content;
	
	/**
	 * 执行时间,小计划开始执行时间
	 * 保存前需要校验是否存在重叠时间的计划执行，如果有则提醒。
	 */
	@FieldMeta(value="开始执行时间")
	private Time executionTime;
	
	/**
	 * 预期结束时间
	 */
	@FieldMeta(value="执行结束时间")
	private Time endTime;
	
	/**
	 * 周期 
	 */
	@FieldMeta(value="周期")
	private Integer period;
	
	/**
	 * 单位 1-小时（弃用）  2-天 3-周  4-月
	 */
	@FieldMeta(value="时间单位")
	private Byte unit;
	
	/**
	 * 计划第一次开始的日期
	 */
	private Date startDate;
	
	/**
	 * 对应的目标的id
	 */
	private Integer targetId;
	
	/**
	 * 删除理由
	 */
	private String deleteReason;
	
	/**
	 * 当前将要或正在执行的计划动作
	 */
	private PlanAction action;
	
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

	public Integer getPeriod() {
		return period;
	}
    
	public void setPeriod(Integer period) {
		this.period = period;
	}
	
    @JsonFormat(pattern="HH:mm")
	public Time getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Time executionTime) {
		this.executionTime = executionTime;
	}
	
    @JsonFormat(pattern="HH:mm")
	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
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

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	@JsonFormat(pattern="YYYY年MM月dd日")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public PlanAction getAction() {
		return action;
	}

	public void setAction(PlanAction action) {
		this.action = action;
	}

	
	
	
	

}
