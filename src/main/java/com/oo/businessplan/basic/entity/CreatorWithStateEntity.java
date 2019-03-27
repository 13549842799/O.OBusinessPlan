package com.oo.businessplan.basic.entity;

import java.sql.Timestamp;

public abstract class CreatorWithStateEntity<T> extends StateAbleEntity<T> {
	
	private Integer creator;
	private Timestamp createTime;
	
	public CreatorWithStateEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CreatorWithStateEntity(T id, Byte delflag, Byte state) {
		super(id, delflag, state);
		// TODO Auto-generated constructor stub
	}
	public CreatorWithStateEntity(T id, Byte delflag) {
		super(id, delflag);
		// TODO Auto-generated constructor stub
	}
	public CreatorWithStateEntity(T id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
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
