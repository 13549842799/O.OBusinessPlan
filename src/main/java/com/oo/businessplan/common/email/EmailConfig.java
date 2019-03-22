package com.oo.businessplan.common.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailConfig extends Authenticator{
	
	public String USERNAME = "";//邮箱用户名
    public String PASSWORD = "";//授权码
    
    private String emailName ;//发送方的邮箱地址
	
	private String mail_smtp_host;//邮箱服务器地址
	
	private boolean mail_smtp_auth = true;//是否校检
	
	protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(USERNAME, PASSWORD);
    }

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String getMail_smtp_host() {
		return mail_smtp_host;
	}

	public void setMail_smtp_host(String mail_smtp_host) {
		this.mail_smtp_host = mail_smtp_host;
	}

	public boolean getMail_smtp_auth() {
		return mail_smtp_auth;
	}

	public void setMail_smtp_auth(boolean mail_smtp_auth) {
		this.mail_smtp_auth = mail_smtp_auth;
	}
	
	

}
