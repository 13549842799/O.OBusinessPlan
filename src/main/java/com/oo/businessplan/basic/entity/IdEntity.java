package com.oo.businessplan.basic.entity;

import java.io.Serializable;

public abstract class IdEntity <T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4275034184543847794L;
	private T id; //主键
	
	public IdEntity () {}
	
	public IdEntity (T id) {
		super();
		this.id = id;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
	
}
