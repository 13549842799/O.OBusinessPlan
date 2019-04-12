package com.oo.businessplan.article.pojo.entity;

import java.beans.Transient;

import com.oo.businessplan.basic.entity.CreatorWithStateEntity;

/**
 * 标签表，包括系统标签和用户自定义标签,所有分类的文章使用同一套标签
 * @author cyz
 *
 */
public class Label extends CreatorWithStateEntity<Integer> {
	
	private static final long serialVersionUID = 472330572592879816L;
	
	public static final byte system = 0;

	private String name;
	
	private Byte type;
	
	private Integer adminId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
	@Transient
	public boolean isSystem() {
		return this.type == 0;
	}

}
