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
	
	/**
	 * 1-日记 4-小说
	 */
	private Byte targetType;
	
	/**
	 * 关联的实体的id 日记,小说,
	 */
	private Integer targetId;

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
	
	public Byte getTargetType() {
		return targetType;
	}


	public void setTargetType(Byte targetType) {
		this.targetType = targetType;
	}


	public Integer getTargetId() {
		return targetId;
	}


	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}


	/**
	 * 为前端提供check属性初始值
	 * @return
	 */
	public boolean getCheck() {
		return false;
	}


}
