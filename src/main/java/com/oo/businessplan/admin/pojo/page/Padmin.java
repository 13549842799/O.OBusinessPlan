package com.oo.businessplan.admin.pojo.page;

import java.io.Serializable;
import java.util.List;

import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.authority.pojo.Role;

public class Padmin extends Admin implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1803881662131601874L;
    private String relatedName;
    private List<Role> roles;
	 
	public String getRelatedName() {
		return relatedName;
	}
	public void setRelatedName(String relatedName) {
		this.relatedName = relatedName;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "Padmin [relatedName=" + relatedName + "]";
	}
	 
	 

}
