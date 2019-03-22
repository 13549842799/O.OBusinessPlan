package com.oo.businessplan.authority.pojo;

public final class AuthorityWithKey extends Authority  {

	private static final long serialVersionUID = -3778249512939499830L;
	private String key;
	private String resourceName;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public String toString() {
		return "AuthorityWithKey [key=" + key + ", id=" + id + ", roid=" + roid + ", reid=" + reid + ", type=" + type
				+ ", delflag=" + delflag + "]";
	}

	

	
	
	

}
