
package com.oo.businessplan.common.enumeration;

public enum AuthorityCode {
	
	LOGIN_ALL("登陆权限_全","100"),
	LOGIN_CENTER("登陆权限_总站","100100"),
	LOGIN_MUSIC("登陆权限_音乐网站","100101"),
	LOGIN_MESSAGE("登陆社会咨询网站的权限","100102"),
	OPERATE_EMPLOYEE("操作职员信息的权限","101"),
	EMPLOYEE_ADD("增加职员的权限","101100"),
	EMPLOYEE_UPDATE("更改职员信息的权限","101101"),
	EMPLOYEE_SELECT("查看职员信息的权限","101102"),
	EMPLOYEE_DEL("删除职员信息的权限","101103");
	
	private String name;
	private String code;
	
	private AuthorityCode(String name,String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
