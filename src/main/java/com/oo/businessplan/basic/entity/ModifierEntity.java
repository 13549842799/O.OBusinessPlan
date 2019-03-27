package com.oo.businessplan.basic.entity;

import java.sql.Timestamp;

public class ModifierEntity<T> extends CreatorEntity<T> {
	
    private Integer modifier;
	
	private Timestamp modifierTime;

	public ModifierEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ModifierEntity(Byte delflag) {
		super(delflag);
		// TODO Auto-generated constructor stub
	}

	public ModifierEntity(T id, Byte delflag) {
		super(id, delflag);
		// TODO Auto-generated constructor stub
	}

	public ModifierEntity(T id) {
		super(id);
		// TODO Auto-generated constructor stub
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
