package com.oo.businessplan.article.pojo.entity;

import com.oo.businessplan.basic.entity.CreatorEntity;

/**
 * 标签表，包括系统标签和用户自定义标签,所有分类的文章使用同一套标签
 * @author cyz
 *
 */
public class Label extends CreatorEntity<Integer> {
	
	private static final long serialVersionUID = 472330572592879816L;
	
	public static final byte SYSTEM = 0;
	
	public static final byte USER = 1;
	
	public Label () {}
	

	public Label(Integer id, Byte delflag) {
		super(id, delflag);
	}

	public Label(Integer id) {
		super(id);
	}

	private String name;
	
	private Integer adminId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}


}
