package com.oo.businessplan.admin.pojo.form;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.oo.businessplan.common.pageModel.PageParams;

@Alias("departmentForm")
public class DepartmentForm extends PageParams implements Serializable{
	

	private static final long serialVersionUID = -7151168779423769932L;
	private Integer[] ids;
	private Integer[] pids;
	private String dname;
	private String dcode;
	private Integer minCount;//部门人数
	private Integer maxCount;//最大部门人数
	private Byte state;
	private Byte delflag;
	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	public Integer[] getPids() {
		return pids;
	}
	public void setPids(Integer[] pids) {
		this.pids = pids;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getDcode() {
		return dcode;
	}
	public void setDcode(String dcode) {
		this.dcode = dcode;
	}
	public Integer getMinCount() {
		return minCount;
	}
	public void setMinCount(Integer minCount) {
		this.minCount = minCount;
	}
	public Integer getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	public Byte getDelflag() {
		return delflag;
	}
	public void setDelflag(Byte delflag) {
		this.delflag = delflag;
	}
    
	
	

}
