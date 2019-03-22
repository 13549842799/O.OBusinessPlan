package com.oo.businessplan.admin.pojo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Admin implements Serializable{
		
	
	private static final long serialVersionUID = 8497855686170324041L;
	private Integer id;
	private String accountname;
	private String password;
	private String nikename;
	private String avatar;  
	private Integer relatedid;
	private String autologin_mac;
	private Timestamp createtime;
	private Byte state;
	private Byte delflag;
	
	public Admin() {super();}
	
	public Admin(Integer id) {
		this();
		this.id = id;
	}
	
	public Admin(Integer id,Byte delflag) {
		this(id);
		this.delflag = delflag;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNikename() {
		return nikename;
	}
	public void setNikename(String nikename) {
		this.nikename = nikename;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Integer getRelatedid() {
		return relatedid;
	}
	public void setRelatedid(Integer relatedid) {
		this.relatedid = relatedid;
	}
	public String getAutologin_mac() {
		return autologin_mac;
	}
	public void setAutologin_mac(String autologin_mac) {
		this.autologin_mac = autologin_mac;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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
	@Override
	public String toString() {
		return "Admin [id=" + id + ", accountname=" + accountname + ", password=" + password + ", nikename=" + nikename
				+ ", avatar=" + avatar + ", relatedid=" + relatedid + ", autologin_mac=" + autologin_mac
				+ ", createtime=" + createtime + ", state=" + state + ", delflag=" + delflag + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountname == null) ? 0 : accountname.hashCode());
		result = prime * result + ((autologin_mac == null) ? 0 : autologin_mac.hashCode());
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((delflag == null) ? 0 : delflag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nikename == null) ? 0 : nikename.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((relatedid == null) ? 0 : relatedid.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admin other = (Admin) obj;
		if (accountname == null) {
			if (other.accountname != null)
				return false;
		} else if (!accountname.equals(other.accountname))
			return false;
		if (autologin_mac == null) {
			if (other.autologin_mac != null)
				return false;
		} else if (!autologin_mac.equals(other.autologin_mac))
			return false;
		if (avatar == null) {
			if (other.avatar != null)
				return false;
		} else if (!avatar.equals(other.avatar))
			return false;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		if (delflag == null) {
			if (other.delflag != null)
				return false;
		} else if (!delflag.equals(other.delflag))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nikename == null) {
			if (other.nikename != null)
				return false;
		} else if (!nikename.equals(other.nikename))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (relatedid == null) {
			if (other.relatedid != null)
				return false;
		} else if (!relatedid.equals(other.relatedid))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
    
	

	
	

}
