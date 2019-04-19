package com.oo.businessplan.article.pojo;

import com.oo.businessplan.basic.entity.ModifierEntity;
import com.oo.businessplan.basic.entity.ModifierWithStateEntity;

/**
 * 
 * @author cyz
 *
 * @param <T>
 */
public abstract class AbstractArticle<T> extends ModifierEntity<T> {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 3606072351373120259L;

	private String title; //标题
	
	private String content; //内容 
	
	private Integer classify; // 分类
	
	public AbstractArticle() {
		super();
	}
	
	public AbstractArticle(T id, Byte delflag) {
		super(id, delflag);
	}

	public AbstractArticle(T id) {
		super(id);
	}

	
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
