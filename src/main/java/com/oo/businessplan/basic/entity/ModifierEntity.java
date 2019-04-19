package com.oo.businessplan.basic.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ModifierEntity<T> extends CreatorEntity<T> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4813606217462294137L;

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

	@JsonFormat(pattern="YYYY年MM月dd日  HH:mm:ss")
	public Timestamp getModifierTime() {
		return modifierTime;
	}

	public void setModifierTime(Timestamp modifierTime) {
		this.modifierTime = modifierTime;
	}
	
	

}
