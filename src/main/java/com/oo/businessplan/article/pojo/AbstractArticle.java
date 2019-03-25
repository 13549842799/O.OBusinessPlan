package com.oo.businessplan.article.pojo;

import com.oo.businessplan.basic.entity.ModifierEntity;

public abstract class AbstractArticle<T> extends ModifierEntity<T> {
    
	private String title;
	
	private String content;
	
	private Byte type;

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

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
	
}
