package com.oo.businessplan.target.pojo.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.oo.businessplan.basic.entity.IdEntity;

public class PlanAction extends IdEntity<Long> {
	
	/**
	 * 不可执行  ，当动作被生成时为不可知性，当时间到达该动作执行时间后，则改变等待执行，点击进行后变为正在执行
	 */
	public static final byte UNSTART = 0; //不可执行
	
	/**
	 * 等待执行
	 */
	public static final byte WAITSTART = 1;
	
	/**
	 * 正在执行
	 */
	public static final byte ACTIONING = 2;

	/**
	 * 执行结束
	 */
	public static final byte COMPLETE = 3;

	/**
	 * 超时结束
	 */
	public static final byte OVERCOMPLETE = 4;

	/**
	 * 没有完成
	 */
	public static final byte UNCOMPLETE = 5;

	/**
	 * 放弃
	 */
	public static final byte GIVEUP = 6;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3539248306510355439L;
	
	/**
	 * 当前执行动作是对应计划的最新次数
	 */
	private int num;

	/**
	 * 实际开始时间
	 */
	private Timestamp startTime;
	
	/**
	 * 实际结束时间
	 */
	private Timestamp endTime;
	
	/**
	 * 消耗时间
	 */
	private Long consumeTime;
	
	/**
	 * 超时时长
	 */
	private Long overTime;
	
	/**
	 * 执行结果 0-不可执行 1-等待执行 2-正在执行 3-执行完成 4-超时完成  5-没有完成 6.放弃执行
	 */
	private Byte result;
	
	/**
	 * 没有完成的客观原因
	 */
	private String reason;
	
	private Integer targetPlanId;
	
	/**
	 * 动作执行的日期
	 */
	private Date actionDate;

	
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Long getOverTime() {
		return overTime;
	}

	public void setOverTime(Long overTime) {
		this.overTime = overTime;
	}

	public Byte getResult() {
		return result;
	}

	public void setResult(Byte result) {
		this.result = result;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Long consumeTime) {
		this.consumeTime = consumeTime;
	}

	public Integer getTargetPlanId() {
		return targetPlanId;
	}

	public void setTargetPlanId(Integer targetPlanId) {
		this.targetPlanId = targetPlanId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	@Override
	public String toString() {
		return "PlanAction [num=" + num + ", startTime=" + startTime + ", endTime=" + endTime + ", consumeTime="
				+ consumeTime + ", overTime=" + overTime + ", result=" + result + ", reason=" + reason
				+ ", targetPlanId=" + targetPlanId + ", actionDate=" + actionDate + "]";
	}
	
	

}
