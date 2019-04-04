package com.oo.businessplan.authority.pojo;

import java.io.Serializable;
import java.util.List;

public final class AuthorityWithKey extends Authority implements Serializable  {

	private static final long serialVersionUID = -3778249512939499830L;
	private String key;
	private String resourceName;
	private Integer rPid;
	private Integer rid;
	private List<AuthorityWithKey> childs;

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

	public Integer getrPid() {
		return rPid;
	}

	public void setrPid(Integer rPid) {
		this.rPid = rPid;
	}   
	public List<AuthorityWithKey> getChilds() {
		return childs;
	}

	public void setChilds(List<AuthorityWithKey> childs) {
		this.childs = childs;
	}
	
	

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	@Override
	public String toString() {
		return "AuthorityWithKey [key=" + key + ", id=" + this.getId() + ", roid=" + this.getRoid() + ", reid=" + this.getReid() + ", type=" + this.getType()
				+ ", delflag=" + this.getDelflag() + "]";
	}

	

	
	
	

}
