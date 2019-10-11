package com.oo.businessplan.target.pojo.entity;

import java.sql.Timestamp;

import com.oo.businessplan.basic.entity.IdEntity;

public class PlanAction extends IdEntity<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3539248306510355439L;

	/**
	 * 实际开始时间
	 */
	private Timestamp startTime;
	
	/**
	 * 实际结束时间
	 */
	private Timestamp endTime;
	
	/**
	 * 超时时长
	 */
	private Long overTime;
	
	/**
	 * 执行结果 0-没有执行 1-超时完成  2-按时完成
	 */
	private Byte result;
	
	/**
	 * 没有完成的客观原因
	 */
	private String reason;

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
	
	

}
