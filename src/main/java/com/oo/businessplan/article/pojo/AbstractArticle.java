package com.oo.businessplan.article.pojo;

import com.oo.businessplan.basic.entity.ModifierEntity;

/**
 * 
 * @author cyz
 *
 * @param <T>
 */
public abstract class AbstractArticle<T> extends ModifierEntity<T> {
    
	private String title; //标题
	
	private String content; //内容 
	
	private Integer classify; // 分类

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}
	
}
