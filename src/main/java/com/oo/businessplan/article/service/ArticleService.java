package com.oo.businessplan.article.service;

import java.util.List;

/**
 * 文章类的相关接口
 * @author cyz
 *
 */
public interface ArticleService<T> {
	
	/**
	 * 通过标题模糊查询对应文章的文章列表 (id, title)
	 * @param title
	 * @return
	 */
	List<T> searchTitle(String title);

}
