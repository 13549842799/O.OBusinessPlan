package com.oo.businessplan.basic.entity;

import java.sql.Timestamp;

public abstract class CreatorEntity<T> extends StateAbleEntity<T> {
	
	private Integer creator;
	private Timestamp createTime;
	
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	

}
