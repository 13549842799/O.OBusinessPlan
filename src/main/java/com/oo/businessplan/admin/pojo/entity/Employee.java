package com.oo.businessplan.admin.pojo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.oo.businessplan.authority.pojo.Authority;

@Alias("employee")
public class Employee implements Serializable {
	
	
	private static final long serialVersionUID = 254542242249640980L;
	private Integer eid;
	private String ecode;
	private String name;
	private String sex;
	private Timestamp birth;
	private String idCard;//身份证号码
	private Byte marriageId; //婚姻状况 0-未婚 1-已婚
	private Integer nationId ;//民族id
	private String nationName;//名族名称
	private String phone;
	private String email;
	private String qq;
	private String address;
	private Integer qualificationId;//学历id
	private String qualificationName;//学历名称
	private String school;
	private String avatar;
	private Integer positionId;//职位id
    private String positionName;
	private Integer departmentId;//部门id
	private String departmentName;
	private Byte stateId;///工作状态
	private String stateName;
	private BigDecimal salary;//薪水
	private Integer salaryTypeId;//薪水类型id
	private String salaryTypeName;//
	private Timestamp beginTime;//入职时间
	private Timestamp endTime;//离职时间
	private Integer creator;//
	private String creatorName;
	private Timestamp createTime;
	private Byte delflag;//删除标志 0-删除 1-正常
	private List<Authority> authorities;
	private Integer adminId;
	
	public Employee(){}
	
	public Employee(Integer eid, String ecode, String name, String sex, Timestamp birth, String idCard, Byte marriageId,
			Integer nationId, String nationName, String phone, String email, String qq, String address,
			Integer qualificationId, String qualificationName, String school, String avatar, Integer positionId,
			String positionName, Integer departmentId, String departmentName, Byte stateId, String stateName,
			BigDecimal salary, Integer salaryTypeId, String salaryTypeName, Timestamp beginTime, Timestamp endTime,
			Integer creator, String creatorName, Timestamp createTime, Byte delflag, List<Authority> authorities,
			Integer adminId) {
		super();
		this.eid = eid;
		this.ecode = ecode;
		this.name = name;
		this.sex = sex;
		this.birth = birth;
		this.idCard = idCard;
		this.marriageId = marriageId;
		this.nationId = nationId;
		this.nationName = nationName;
		this.phone = phone;
		this.email = email;
		this.qq = qq;
		this.address = address;
		this.qualificationId = qualificationId;
		this.qualificationName = qualificationName;
		this.school = school;
		this.avatar = avatar;
		this.positionId = positionId;
		this.positionName = positionName;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.stateId = stateId;
		this.stateName = stateName;
		this.salary = salary;
		this.salaryTypeId = salaryTypeId;
		this.salaryTypeName = salaryTypeName;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.creator = creator;
		this.creatorName = creatorName;
		this.createTime = createTime;
		this.delflag = delflag;
		this.authorities = authorities;
		this.adminId = adminId;
	}



	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
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
	public Timestamp getBirth() {
		return birth;
	}
	public void setBirth(Timestamp birth) {
		this.birth = birth;
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
	public Integer getNationId() {
		return nationId;
	}
	public void setNationId(Integer nationId) {
		this.nationId = nationId;
	}
	public String getNationName() {
		return nationName;
	}
	public void setNationName(String nationName) {
		this.nationName = nationName;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getQualificationId() {
		return qualificationId;
	}
	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}
	public String getQualificationName() {
		return qualificationName;
	}
	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Byte getStateId() {
		return stateId;
	}
	public void setStateId(Byte stateId) {
		this.stateId = stateId;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public Integer getSalaryTypeId() {
		return salaryTypeId;
	}
	public void setSalaryTypeId(Integer salaryTypeId) {
		this.salaryTypeId = salaryTypeId;
	}
	public String getSalaryTypeName() {
		return salaryTypeName;
	}
	public void setSalaryTypeName(String salaryTypeName) {
		this.salaryTypeName = salaryTypeName;
	}
	public Timestamp getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Byte getDelflag() {
		return delflag;
	}
	public void setDelflag(Byte delflag) {
		this.delflag = delflag;
	}
	public List<Authority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
	
	

}
