package com.oo.businessplan.article.pojo.entity;

import com.oo.businessplan.basic.entity.CreatorWithStateEntity;

/**
 * 文章分类表，包括系统分类和用户自定义分类
 * @author cyz
 *
 */
public class ArticleClassify extends CreatorWithStateEntity<Integer> {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
