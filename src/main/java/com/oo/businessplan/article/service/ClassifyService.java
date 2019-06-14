package com.oo.businessplan.article.service;

import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.pojo.entity.Classify;
import com.oo.businessplan.basic.service.BaseService;

public interface ClassifyService extends BaseService<Classify> {
	
	/**
	 * 根据条件获取唯一的分类
	 * @param cls
	 * @return
	 */
	Classify getUnique(Classify cls);
	
	/**
	 * 判断用户在目标文章类型下是否存在同名的分类, 
	 * 这个判断也包括系统的分类，如果存在系统分类的名称是 <name> 的话，则与creator无关。
	 * @param name
	 * @param childType
	 * @return
	 */
	boolean checkExists(String name, int creator, byte childType);
	
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

	/**
	 * 获取分页类型的列表
	 * @param cls
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Classify> getPage(Classify cls, Integer pageNum, Integer pageSize);

}
