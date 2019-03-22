package com.oo.businessplan.authority.pojo;

import java.io.Serializable;

public class RolePage extends Role implements Serializable{

	private static final long serialVersionUID = -1758523004449458984L;
	private String creatorName;
	private String modifierName;
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getModifierName() {
		return modifierName;
	}
	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}
	
	

}
