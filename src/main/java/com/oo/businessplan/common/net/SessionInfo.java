package com.oo.businessplan.common.net;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SessionInfo implements Serializable{


	private static final long serialVersionUID = -7184519583829833740L;
	private long id;
	private String name;
	private String ip;
	private String token;
	private Long availableDate;
	private String mac;
	private Map<String,String> resourceList = new HashMap<>();//编号 注销地址

	public long getId() {
		
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Map<String, String> getResourceList() {
		return resourceList;
	}
	public void setResourceList(Map<String, String> resourceList) {
		this.resourceList = resourceList;
	}
	public Long getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(Long availableDate) {
		this.availableDate = availableDate;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	
	
	
	
}