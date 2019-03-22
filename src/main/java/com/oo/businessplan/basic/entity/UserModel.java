package com.oo.businessplan.basic.entity;

import java.sql.Timestamp;
import java.util.Date;

public class UserModel {
	
	protected Integer id;
	protected String code;//编号 （账号）
	protected String account;//账号
	protected String password;//密码
	protected String name;
	protected String sex;
	protected String avatar;//头像路径
	protected String phone;//电话号码
	protected String email;//邮箱
	protected String QQ;//qq
	protected String address;
	protected Date birth;//出生日期
	protected Timestamp createtime;//注册时间
	protected int status;// 1正常 0禁用
	
	public UserModel() {
		super();
	}
	public UserModel(Integer id, String code, String account, String password, String name, String sex,
			String avatar, String phone, String email, String qQ, String address, Date birth, Timestamp createtime) {
		super();
		this.id = id;
		this.code = code;
		this.account = account;
		this.password = password;
		this.name = name;
		this.sex = sex;
		this.avatar = avatar;
		this.phone = phone;
		this.email = email;
		this.QQ = qQ;
		this.address = address;
		this.birth = birth;
		this.createtime = createtime;
	}
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
