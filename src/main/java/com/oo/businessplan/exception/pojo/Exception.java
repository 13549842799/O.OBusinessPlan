package com.oo.businessplan.exception.pojo;

import java.sql.Timestamp;

import com.oo.businessplan.basic.entity.IdEntity;

public class Exception extends IdEntity<Integer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7883219933451437377L;

	private Integer admin;
	
	private String request;
	
	private String position;
	
	private String message;
	
	private Timestamp createTime;
	
	public Exception() {}

	public Exception(Integer admin, String request, String position, String message, Timestamp createTime) {
		super();
		this.admin = admin;
		this.request = request;
		this.position = position;
		this.message = message;
		this.createTime = createTime;
	}

	public Integer getAdmin() {
		return admin;
	}

	public void setAdmin(Integer admin) {
		this.admin = admin;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
