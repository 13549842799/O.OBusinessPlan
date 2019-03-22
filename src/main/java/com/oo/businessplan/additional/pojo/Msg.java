package com.oo.businessplan.additional.pojo;

import java.sql.Timestamp;

public class Msg {
	
	
	private Integer id;
	private String code;
	private String phoneNo;
	private Timestamp createTime;
	private Timestamp validTime;
	private Byte delflag;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getValidTime() {
		return validTime;
	}
	public void setValidTime(Timestamp validTime) {
		this.validTime = validTime;
	}
	public Byte getDelflag() {
		return delflag;
	}
	public void setDelflag(Byte delflag) {
		this.delflag = delflag;
	}
	
	

}
