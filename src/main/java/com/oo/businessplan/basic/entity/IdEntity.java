package com.oo.businessplan.basic.entity;

public abstract class IdEntity <T> {
	
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
