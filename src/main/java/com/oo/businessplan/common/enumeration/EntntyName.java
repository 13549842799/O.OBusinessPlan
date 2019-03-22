package com.oo.businessplan.common.enumeration;

public enum EntntyName {

	
	ADMIN("admin"),
	USER("user");
	
	private String val;
	
	private EntntyName(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	
	
}
