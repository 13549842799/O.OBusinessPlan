package com.oo.businessplan.admin.pojo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

@Alias("positions")
public class Positions implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 957005616382961570L;
	private Integer poid;
	private String pname;
	private String pcode;
	private String remark;
	private Integer creator;
	private Timestamp createTime;
	private Byte state;
	private Byte delflag;
	public Integer getPoid() {
		return poid;
	}
	public void setPoid(Integer poid) {
		this.poid = poid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	public Byte getDelflag() {
		return delflag;
	}
	public void setDelflag(Byte delflag) {
		this.delflag = delflag;
	}
	
	

}
