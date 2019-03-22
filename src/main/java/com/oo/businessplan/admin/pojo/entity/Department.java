package com.oo.businessplan.admin.pojo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Department implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3042183608360922004L;
	private Integer did;
	private Integer pid;
	private String dname;
	private String dcode;
	private String remark;
	private Integer count;//部门人数
	private Byte state;
	private Integer creator;
	private Timestamp createTiime;
	private Byte delflag;
	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
		this.did = did;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getDcode() {
		return dcode;
	}
	public void setDcode(String dcode) {
		this.dcode = dcode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public Timestamp getCreateTiime() {
		return createTiime;
	}
	public void setCreateTiime(Timestamp createTiime) {
		this.createTiime = createTiime;
	}
	public Byte getDelflag() {
		return delflag;
	}
	public void setDelflag(Byte delflag) {
		this.delflag = delflag;
	}	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Department [did=" + did + ", pid=" + pid + ", dname=" + dname + ", dcode=" + dcode + ", remark="
				+ remark + ", state=" + state + ", creator=" + creator + ", createTiime=" + createTiime + ", delflag="
				+ delflag + "]";
	}
	
	

}
