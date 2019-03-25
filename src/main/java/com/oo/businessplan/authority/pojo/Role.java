package com.oo.businessplan.authority.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.oo.businessplan.basic.entity.ModifierEntity;

/**
 * 角色类
 * @author cyz
 *
 */
public class Role extends ModifierEntity<Integer> implements Serializable{
	
	private static final long serialVersionUID = 3142859976836736060L;
	private String code;//编码
	private Integer sort;
	private String name;
	private String describes;
	private Timestamp updateNo;
	
	public Role(){}
	
	public Role(Integer id) {
		super();
		this.setId(id);
	}
	
	public Role(String code){
		this.code = code;
	}
	
	public Role(Integer id, Byte delflag) {
		this(id);
		this.setDelflag(delflag);
	}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Timestamp getUpdateNo() {
		return updateNo;
	}

	public void setUpdateNo(Timestamp updateNo) {
		this.updateNo = updateNo;
	}
	
	
	
	

}
