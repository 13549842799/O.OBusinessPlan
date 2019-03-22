package com.oo.businessplan.common.enumeration;

public enum MsgTypeCode {
	
	ALTER("修改密码",(byte)1),
	FORGET("忘记密码",(byte)2);
	
	private String name;
	private byte code;
	
	private MsgTypeCode(String name,byte code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getCode() {
		return code;
	}

	public void setCode(byte code) {
		this.code = code;
	}
	
	

}
