package com.oo.businessplan.article.pojo.entity;

import java.util.Date;

import com.oo.businessplan.article.pojo.AbstractArticle;

public class Diary extends AbstractArticle<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8616956690644129662L;

	private Date date ;// 日记日期
	
	private String labels; //格式 标签1,标签2,标签3...

	public Diary() {
		super();
	}
	
	public Diary(Integer id, Integer creator, Byte delflag) {
		this(id, delflag);
		this.setCreator(creator);
	}

	public Diary(Integer id, Byte delflag) {
		super(id, delflag);
	}

	public Diary(Integer id) {
		super(id);
	}
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}
	
	

}
