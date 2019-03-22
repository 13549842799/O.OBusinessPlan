package com.oo.businessplan.admin.pojo.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import com.oo.businessplan.common.pageModel.PageParams;

public class EmployeeForm extends PageParams implements Serializable{
	
	  
	private static final long serialVersionUID = -338169458203367019L;
	private Integer[] eids;
	private String ecode;
	private String name;
	private String sex;
	private String idCard;//身份证号码
	private Byte marriageId; //婚姻状况 0-未婚 1-已婚
	private String phone;
	private String email;
	private String qq;
	private Integer qualificationId;//学历id
	private Integer[] positionIds;//职位id
	private Integer[] departmentIds;//部门id
	private Byte stateId;///工作状态
	private BigDecimal minSalary;
	private BigDecimal maxSalary;
	private Timestamp minBeginTime;
	private Timestamp maxBeginTime;
	private Timestamp minEndTime;
	private Timestamp maxEndTime;
	private Integer salaryTypeId;//薪水类型id
	private Byte delflag;//删除标志 0-删除 1-正常
	public Integer[] getEids() {
		return eids;
	}
	public void setEids(Integer[] eids) {
		this.eids = eids;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Byte getMarriageId() {
		return marriageId;
	}
	public void setMarriageId(Byte marriageId) {
		this.marriageId = marriageId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Integer getQualificationId() {
		return qualificationId;
	}
	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}
	public Integer[] getPositionIds() {
		return positionIds;
	}
	public void setPositionIds(Integer[] positionIds) {
		this.positionIds = positionIds;
	}
	public Integer[] getDepartmentIds() {
		return departmentIds;
	}
	public void setDepartmentIds(Integer[] departmentIds) {
		this.departmentIds = departmentIds;
	}
	public Byte getStateId() {
		return stateId;
	}
	public void setStateId(Byte stateId) {
		this.stateId = stateId;
	}
	public BigDecimal getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(BigDecimal minSalary) {
		this.minSalary = minSalary;
	}
	public BigDecimal getMaxSalary() {
		return maxSalary;
	}
	public void setMaxSalary(BigDecimal maxSalary) {
		this.maxSalary = maxSalary;
	}
	public Timestamp getMinBeginTime() {
		return minBeginTime;
	}
	public void setMinBeginTime(Timestamp minBeginTime) {
		this.minBeginTime = minBeginTime;
	}
	public Timestamp getMaxBeginTime() {
		return maxBeginTime;
	}
	public void setMaxBeginTime(Timestamp maxBeginTime) {
		this.maxBeginTime = maxBeginTime;
	}
	public Timestamp getMinEndTime() {
		return minEndTime;
	}
	public void setMinEndTime(Timestamp minEndTime) {
		this.minEndTime = minEndTime;
	}
	public Timestamp getMaxEndTime() {
		return maxEndTime;
	}
	public void setMaxEndTime(Timestamp maxEndTime) {
		this.maxEndTime = maxEndTime;
	}
	public Integer getSalaryTypeId() {
		return salaryTypeId;
	}
	public void setSalaryTypeId(Integer salaryTypeId) {
		this.salaryTypeId = salaryTypeId;
	}
	public Byte getDelflag() {
		return delflag;
	}
	public void setDelflag(Byte delflag) {
		this.delflag = delflag;
	}
	
	
	
	
	   

}
