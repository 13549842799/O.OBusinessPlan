package com.oo.businessplan.common.pageModel;

import com.oo.businessplan.basic.entity.UserModel;

/**
 * 职员的页面对象
 * @author admin
 *
 */
public class Puser extends UserModel {
	
	private String creator;//格式：(编号)姓名
	private String bus_role;//公司角色
	private String sys_role;//系统角色
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getBus_role() {
		return bus_role;
	}
	public void setBus_role(String bus_role) {
		this.bus_role = bus_role;
	}
	public String getSys_role() {
		return sys_role;
	}
	public void setSys_role(String sys_role) {
		this.sys_role = sys_role;
	}
	
	

}
