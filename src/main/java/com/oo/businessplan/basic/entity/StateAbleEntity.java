package com.oo.businessplan.basic.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class StateAbleEntity<T> extends DeleteAbleEntity<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8091022330441023071L;
	private Byte state; //状态  0-禁用 1-启用
	
	public StateAbleEntity() {}
	
	public StateAbleEntity(T id) {
		super(id);
	}
	
	public StateAbleEntity(T id, Byte delflag) {
		super(id, delflag);
	}
	
	public StateAbleEntity(T id,Byte delflag, Byte state) {
		this(id, delflag);
		this.state = state;
	}

	public Byte getState() {
		return state;
	}
    
	@JsonProperty(defaultValue="1")
	public void setState(Byte state) {
		this.state = state;
	}
	
	

}
