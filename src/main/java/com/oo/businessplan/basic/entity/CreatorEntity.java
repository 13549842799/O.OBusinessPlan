package com.oo.businessplan.basic.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class CreatorEntity<T> extends DeleteAbleEntity<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7650539991301923796L;

	private Integer creator;
	
	private Timestamp createTime;
	
	
	
	public CreatorEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CreatorEntity(Byte delflag) {
		super(delflag);
		// TODO Auto-generated constructor stub
	}
	public CreatorEntity(T id, Byte delflag) {
		super(id, delflag);
		// TODO Auto-generated constructor stub
	}
	public CreatorEntity(T id) {
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
