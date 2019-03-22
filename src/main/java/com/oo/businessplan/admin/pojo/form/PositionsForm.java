package com.oo.businessplan.admin.pojo.form;


import org.apache.ibatis.type.Alias;

import com.oo.businessplan.common.pageModel.PageParams;

@Alias("positionform")
public class PositionsForm extends PageParams{
	
	private Integer[] poids;
	private String pname;
	private String pcode;
	private String remark;
	private Byte state;
	private Byte delflag;
	public Integer[] getPoids() {
		return poids;
	}
	public void setPoids(Integer[] poids) {
		this.poids = poids;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
