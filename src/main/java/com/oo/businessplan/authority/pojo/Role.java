package com.oo.businessplan.authority.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 角色类
 * @author cyz
 *
 */
public class Role implements Serializable{
	
	private static final long serialVersionUID = 3142859976836736060L;
	private Integer id;
	private String code;//编码
	private Integer sort;
	private String name;
	private String describes;
	private Integer creator;
	private Timestamp createTime;
	private Integer modifier;
	private Timestamp modifierTime;
	private Timestamp updateNo;
	private Byte state;//状态  0-禁用 1-启用
	private Byte delflag;//删除标志 0-删除 1-整除
	
	public Role(Integer id) {
		this.id = id;
	}
	
	public Role(String code){
		this.code = code;
	}
	
	public Role(Integer id, Byte delflag) {
		this(id);
		this.delflag = delflag;
	}
	
	public Role(){}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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

	@JsonProperty(defaultValue="1")
	public void setState(Byte state) {
		this.state = state;
	}

	public Byte getDelflag() {
		return delflag;
	}

	@JsonProperty(defaultValue="1")
	public void setDelflag(Byte delflag) {
		this.delflag = delflag;
	}

	public Integer getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}

	public Timestamp getModifierTime() {
		return modifierTime;
	}

	public void setModifierTime(Timestamp modifierTime) {
		this.modifierTime = modifierTime;
	}

	public Timestamp getUpdateNo() {
		return updateNo;
	}

	public void setUpdateNo(Timestamp updateNo) {
		this.updateNo = updateNo;
	}
	
	
	
	

}
