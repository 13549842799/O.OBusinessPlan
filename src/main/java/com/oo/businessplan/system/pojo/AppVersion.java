package com.oo.businessplan.system.pojo;

import java.sql.Timestamp;

import com.oo.businessplan.basic.entity.DeleteAbleEntity;

/**
 * app的版本类
 * @author admin
 *
 */
public class AppVersion extends DeleteAbleEntity<Integer>{
	
	
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -4146996158467597413L;

	private String versionNo;
	
	private String path;
	
	private String describes;
	
	private Timestamp createTime;
	
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
}
