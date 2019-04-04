package com.oo.businessplan.authority.pojo;

import java.io.Serializable;

public class ResourceAuths extends Resource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8295569681668794396L;
	private Authority authority;

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	
	
}
