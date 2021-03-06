package com.oo.businessplan.article.pojo.entity;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oo.businessplan.basic.entity.CreatorEntity;

/**
 * 
 * @author tangdabing
 *
 * @param <T> 分类对应的文章的类型 
 * @param <R> 对应的文章类id的类型
 */
public class Classify extends CreatorEntity<Integer> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5305374709344822044L;

	/*
	 * 系统分类值
	 */
	public static final byte SYSTEMCLASSIFY = 1; 
	
	/*
	 * 用户自定义分类值
	 */
	public static final byte CUSTOMCLASSIFY = 2;
	
	/**
	 * 日记
	 */
	public static final byte DIARY = 1;
	
	/**
	 * 原本是总结，现在改为备忘
	 */
	public static final byte FinalReport = 2;
	
	public static final byte NOVEL = 4;
	
	public static final Map<Byte, String> mapperTable = new HashMap<>();
	
	{
		mapperTable.put(DIARY, "diary");
		//mapperTable.put(FinalReport, "finalreport");
		mapperTable.put(NOVEL, "novel");
	}
	
	private String name;
	
	private Byte type; // 1-系统  2-自定义
	
	private Byte childType; // 1-日记  2-备忘 3-灵感   4-小说
	
	private int count = 0; //拥有文章数
	
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
	
	/**
	 * 获取当前分类包含的是哪种类型的文章
	 * @return
	 */
	public String getThisTarget() {
		return mapperTable.get(this.childType);
	}
	
	/**
	 * 当前分类是否属于系统分类
	 * @return
	 */
	public boolean systemClassify() {
		return this.type == SYSTEMCLASSIFY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty(defaultValue="1")
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	

}
