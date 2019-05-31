package com.oo.businessplan.admin.pojo.form;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 登录表单
 * @author cyz
 *
 */
public class LoginForm {

	private String code;
	
	private String userName;
	
	private String password;

	@JsonProperty(required=true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty(required=true)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty(required=true)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
