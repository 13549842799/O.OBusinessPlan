package com.oo.businessplan.admin.pojo.page;

import java.io.Serializable;

import com.oo.businessplan.admin.pojo.entity.Admin;

public class Padmin extends Admin implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1803881662131601874L;
	private Integer relatedid;
    private String relatedName;
	 
	public Integer getRelatedid() {
		return relatedid;
	}
	public void setRelatedid(Integer relatedid) {
		this.relatedid = relatedid;
	}
	public String getRelatedName() {
		return relatedName;
	}
	public void setRelatedName(String relatedName) {
		this.relatedName = relatedName;
	}
	 
	 

}
