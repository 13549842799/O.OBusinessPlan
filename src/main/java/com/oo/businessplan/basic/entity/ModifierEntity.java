package com.oo.businessplan.basic.entity;

import java.sql.Timestamp;

public class ModifierEntity<T> extends CreatorEntity<T> {
	
	private Integer modifier;
	private Timestamp modifierTime;
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
