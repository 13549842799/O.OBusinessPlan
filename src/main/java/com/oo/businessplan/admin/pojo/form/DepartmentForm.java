package com.oo.businessplan.admin.pojo.form;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.oo.businessplan.admin.pojo.entity.Department;

@Alias("departmentForm")
public class DepartmentForm extends Department implements Serializable{
	

	private static final long serialVersionUID = -7151168779423769932L;
	private Integer[] ids;
	private Integer[] pids;
	private String dname;
	private String dcode;
	private Integer minCount;//部门人数
	private Integer maxCount;//最大部门人数

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

    
	
	

}
