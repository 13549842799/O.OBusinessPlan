package com.oo.businessplan.basic.entity;

import java.sql.Timestamp;

public class ModifierWithStateEntity<T> extends CreatorWithStateEntity<T> {
	
	private Integer modifier;
	
	private Timestamp modifierTime;
	
	
	
	public ModifierWithStateEntity() {
		super();
	}
	public ModifierWithStateEntity(T id, Byte delflag, Byte state) {
		super(id, delflag, state);
	}
	public ModifierWithStateEntity(T id, Byte delflag) {
		super(id, delflag);
	}
	public ModifierWithStateEntity(T id) {
		super(id);
	}
	
	public Integer getModifier() {
		return modifier;
	}
	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}
	public Timestamp getModifierTime() {
		return modifierTime;
	}
	public void setModifierTime(Timestamp modifierTime) {
		this.modifierTime = modifierTime;
	}
	
	

}
