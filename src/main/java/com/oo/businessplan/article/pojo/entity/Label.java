package com.oo.businessplan.article.pojo.entity;

import com.oo.businessplan.basic.entity.ModifierEntity;

/**
 * 标签类
 * @author cyz
 *
 */
public class Label extends ModifierEntity<Integer> {
	
	private String name;
	
	private String tableName; // 标签关联的表
	
	private Integer type; // 标签所属  0-用户自定义
	
	private Integer adminId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
	

}
