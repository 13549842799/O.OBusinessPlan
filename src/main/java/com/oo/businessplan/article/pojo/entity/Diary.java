package com.oo.businessplan.article.pojo.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oo.businessplan.article.pojo.AbstractArticle;

public class Diary extends AbstractArticle<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7300831563149118606L;

	/**
	 * 
	 */
	

	private Date date ;// 日记日期
	
	private String labels; //格式 标签1,标签2,标签3...
	
	private Byte status; // 日记状态

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
	
	@JsonFormat(pattern="YYYY年MM月dd日  HH:mm:ss")
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

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
	
	

}
