package com.oo.businessplan.additional.pojo;

import java.sql.Timestamp;

public class Msg {
	
	public static final byte PASSWORD = 1; //验证密码类型
	
	public static final byte PHONE = 2; //验证绑定手机号类型
	
	private Integer id;
	private String code;
	private byte type;
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
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
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
