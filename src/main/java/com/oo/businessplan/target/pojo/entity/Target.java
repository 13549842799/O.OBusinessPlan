package com.oo.businessplan.target.pojo.entity;

import java.util.Date;
import java.util.List;

import com.oo.businessplan.basic.entity.CreatorEntity;

public class Target extends CreatorEntity<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6546368015226396621L;

	/**
	 * 原因
	 */
	private String cause;
	
	/**
	 * 目标内容
	 */
	private String content;
	
	/**
	 * 完成标志，做到什么程度才算完成
	 */
	private String finishSign;
	
	/**
	 * 紧急程度  1-远期目标  2-中期目标  3-近期目标  4-紧急目标
	 */
	private Byte level;
	
	/**
	 * 目标类型 1-生活 2-学习 3-工作
	 */
	private Byte type;
	
	/**
	 * 预期完成时间
	 */
	private Date expectFinishTime;
	
	/**
	 * 真正的完成时间
	 */
	private Date realFinishTime;
	
	/**
	 * 是否开启提醒  0-关闭 1-开启
	 */
	private Byte alert;
	
	public List<TargetPlan> plans;
	
	public Boolean onAlert() {
		return alert == 1;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFinishSign() {
		return finishSign;
	}

	public void setFinishSign(String finishSign) {
		this.finishSign = finishSign;
	}

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public Date getExpectFinishTime() {
		return expectFinishTime;
	}

	public void setExpectFinishTime(Date expectFinishTime) {
		this.expectFinishTime = expectFinishTime;
	}

	public Date getRealFinishTime() {
		return realFinishTime;
	}

	public void setRealFinishTime(Date realFinishTime) {
		this.realFinishTime = realFinishTime;
	}

	public Byte getAlert() {
		return alert;
	}

	public void setAlert(Byte alert) {
		this.alert = alert;
	}

	public List<TargetPlan> getPlans() {
		return plans;
	}

	public void setPlans(List<TargetPlan> plans) {
		this.plans = plans;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
	
	
	
}
