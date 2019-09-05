package com.oo.businessplan.admin.pojo.entity;

import java.io.Serializable;

import com.oo.businessplan.basic.entity.CreatorEntity;

public class AccountManager extends CreatorEntity<Integer> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

    private String username;

    private String password;

    private String source;

    private String bind_email;

    private String bind_phone;

    private String remark;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getBind_email() {
        return bind_email;
    }

    public void setBind_email(String bind_email) {
        this.bind_email = bind_email == null ? null : bind_email.trim();
    }

    public String getBind_phone() {
        return bind_phone;
    }

    public void setBind_phone(String bind_phone) {
        this.bind_phone = bind_phone == null ? null : bind_phone.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


}