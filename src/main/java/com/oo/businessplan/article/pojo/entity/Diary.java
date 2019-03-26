package com.oo.businessplan.article.pojo.entity;

import java.util.Date;
import java.util.List;

import com.oo.businessplan.article.pojo.AbstractArticle;

public class Diary extends AbstractArticle<Integer> {
	
	private Date date ;// 日记日期
	
	private List<Label> labels;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}
	
	

}
