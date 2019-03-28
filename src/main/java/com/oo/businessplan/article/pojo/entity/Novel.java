package com.oo.businessplan.article.pojo.entity;

import java.io.Serializable;

import com.oo.businessplan.article.pojo.AbstractArticle;

/**
 * title 书名  content 简介
 * @author cyz
 *
 */
public class Novel extends AbstractArticle<Integer> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2200995135226600806L;

	private String cover; //封面
	
	private String Labels; //标签
	
	private Integer wordsNum;// 整本书字数
	
	

	public Novel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Novel(Integer id, Byte delflag) {
		super(id, delflag);
		// TODO Auto-generated constructor stub
	}

	public Novel(Integer id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getLabels() {
		return Labels;
	}

	public void setLabels(String labels) {
		Labels = labels;
	}

	public Integer getWordsNum() {
		return wordsNum;
	}

	public void setWordsNum(Integer wordsNum) {
		this.wordsNum = wordsNum;
	}
	
	

}
