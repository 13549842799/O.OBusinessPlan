package com.oo.businessplan.admin.pojo.form;

import com.oo.businessplan.admin.pojo.entity.Admin;

public class AdminForm extends Admin{
	
	private static final long serialVersionUID = 5195780158839157318L;
	private String accountname;
	private String nikename;
	private String relatedName;
	private String relatedCode;
	private Byte roleState;
	private Byte roleDelflag;
	
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getNikename() {
		return nikename;
	}
	public void setNikename(String nikename) {
		this.nikename = nikename;
	}
	public String getRelatedName() {
		return relatedName;
	}
	public void setRelatedName(String relatedName) {
		this.relatedName = relatedName;
	}
	public String getRelatedCode() {
		return relatedCode;
	}
	public void setRelatedCode(String relatedCode) {
		this.relatedCode = relatedCode;
	}
	public Byte getRoleDelflag() {
		return roleDelflag;
	}
	public void setRoleDelflag(Byte roleDelflag) {
		this.roleDelflag = roleDelflag;
	}
	public Byte getRoleState() {
		return roleState;
	}
	public void setRoleState(Byte roleState) {
		this.roleState = roleState;
	}
	
	

}
