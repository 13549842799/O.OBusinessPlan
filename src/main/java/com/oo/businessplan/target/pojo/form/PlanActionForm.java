package com.oo.businessplan.target.pojo.form;

import java.text.SimpleDateFormat;

import com.oo.businessplan.target.pojo.entity.PlanAction;

public class PlanActionForm extends PlanAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8295596203932656L;
	
	private int etype = 0;
	
	private String dateStr;

	public int getEtype() {
		return etype;
	}

	public void setEtype(int etype) {
		this.etype = etype;
	}
	
	
	
	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getConsumeTimeStr() {
		if (this.getConsumeTime() == null) {
			return "";
		}
		long hour = this.getConsumeTime()/3600, minute = this.getConsumeTime()%3600/60, second = minute%60;
		return (hour > 0 ? hour + " 小时" : "") + (minute > 0 ? minute + " 分钟" : "") + (second > 0 ? second + " 秒" : "");
	}

	/**
	 * 获取格式化后的时间
	 * @return
	 */
	public String getStartTimeStr() {		
        return this.getStartTime() != null ? SimpleDateFormat.getTimeInstance(2).format(this.getStartTime()) : "";
	}
	
	/**
	 * 获取格式化后的时间
	 * @return
	 */
	public String getEndTimeStr() {		
        return this.getEndTime() != null ? SimpleDateFormat.getTimeInstance(2).format(this.getEndTime()) : "";
	}

}
