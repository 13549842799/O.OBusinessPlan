package com.oo.businessplan.article.pojo.entity;


import com.oo.businessplan.basic.entity.CreatorEntity;

/**
 * 
 * @author tangdabing
 *
 * @param <T> 分类对应的文章的类型 
 * @param <R> 对应的文章类id的类型
 */
public class Classify extends CreatorEntity<Integer> {
	
	private String name;
	
	private Byte type; // 1-系统  2-自定义
	
	private Byte childType; // 1-日记 2-总结 3-备忘  4-灵感 5-小说
	
	public Classify() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Classify(Byte delflag) {
		super(delflag);
		// TODO Auto-generated constructor stub
	}

	public Classify(Integer id, Byte delflag) {
		super(id, delflag);
		// TODO Auto-generated constructor stub
	}

	public Classify(Integer id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

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

	public Byte getChildType() {
		return childType;
	}

	public void setChildType(Byte childType) {
		this.childType = childType;
	}

}
