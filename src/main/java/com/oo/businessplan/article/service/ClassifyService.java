package com.oo.businessplan.article.service;

import com.oo.businessplan.article.pojo.entity.Classify;
import com.oo.businessplan.basic.service.BaseService;

public interface ClassifyService extends BaseService<Classify> {
	
	/**
	 * 检查目标分类下是否存在文章
	 * @param cls
	 * @return
	 */
	default boolean checkArticleExists(Classify cls) {
    	return articleCountOfClassify(cls) > 0; 
    }
	
	/**
	 * 检查目标分类下的文章数
	 * @param cls
	 * @return
	 */
	int articleCountOfClassify(Classify cls);
	
	/**
	 * 把一个分类内的文章移动到另一个分类中
	 * @param cls
	 * @param newClassifyId 新的分类的id
	 */
	void moveArticleFromOldClassify(Classify cls, int newClassifyId);

}
