package com.oo.businessplan.admin.pojo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;

@Alias("employee")
public class Employee implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 3183018036541154472L;
	private Integer id;
	private String ecode;
	private String name;
	private String sex;
	private Timestamp birth;
	private String idCard;//身份证号码
	private Byte marriage; //婚姻状况 0-未婚 1-已婚
	private Integer nation ;//民族id
	private String nationName;//名族名称
	private String phone;
	private String email;
	private String qq;
	private String address;
	private Integer qualification;//学历id
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
	private Integer salaryType;//薪水类型
	private String salaryTypeName;//
	private Timestamp beginTime;//入职时间
	private Timestamp endTime;//离职时间
	private Integer creator;//
	private String creatorName;
	private Timestamp createTime;
	private Byte delflag;//删除标志 0-删除 1-正常
	private Integer adminId;
	
	public Employee(){}
	
	
	public Employee(Integer id, String ecode, String name, String sex, Timestamp birth, String idCard, Byte marriage,
			Integer nation, String nationName, String phone, String email, String qq, String address,
			Integer qualification, String qualificationName, String school, String avatar, Integer positionId,
			String positionName, Integer departmentId, String departmentName, Byte stateId, String stateName,
			BigDecimal salary, Integer salaryType, String salaryTypeName, Timestamp beginTime, Timestamp endTime,
			Integer creator, String creatorName, Timestamp createTime, Byte delflag, Integer adminId) {
		this.id = id;
		this.ecode = ecode;
		this.name = name;
		this.sex = sex;
		this.birth = birth;
		this.idCard = idCard;
		this.marriage = marriage;
		this.nation = nation;
		this.nationName = nationName;
		this.phone = phone;
		this.email = email;
		this.qq = qq;
		this.address = address;
		this.qualification = qualification;
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
		this.salaryType = salaryType;
		this.salaryTypeName = salaryTypeName;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.creator = creator;
		this.creatorName = creatorName;
		this.createTime = createTime;
		this.delflag = delflag;
		this.adminId = adminId;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
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


	public Byte getMarriage() {
		return marriage;
	}


	public void setMarriage(Byte marriage) {
		this.marriage = marriage;
	}


	public Integer getNation() {
		return nation;
	}


	public void setNation(Integer nation) {
		this.nation = nation;
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


	public Integer getQualification() {
		return qualification;
	}


	public void setQualification(Integer qualification) {
		this.qualification = qualification;
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


	public String getStateName() {
		return stateName;
	}


	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	public BigDecimal getSalary() {
		return salary;
	}


	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}


	public Integer getSalaryType() {
		return salaryType;
	}


	public void setSalaryType(Integer salaryType) {
		this.salaryType = salaryType;
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


	public Integer getAdminId() {
		return adminId;
	}


	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}





	
	
	

}
