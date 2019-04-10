package com.oo.businessplan.basic.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class DeleteAbleEntity<T> extends IdEntity<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7754940750598412837L;
	private Byte delflag; //删除标志 0-删除 1-整除
	
	public DeleteAbleEntity() {}

	public DeleteAbleEntity(T id) {
		super(id);
	}

	public DeleteAbleEntity(T id, Byte delflag) {
		this(id);
		this.delflag = delflag;
	}

	public DeleteAbleEntity(Byte delflag) {
		super();
		this.delflag = delflag;
	}

	public Byte getDelflag() {
		return delflag;
	}

	@JsonProperty(defaultValue="1")
	public void setDelflag(Byte delflag) {
		this.delflag = delflag;
	}

	@Override
	public String toString() {
		return super.toString() + ", delflag=" + delflag + " ";
	}
	
	
	
	

}
