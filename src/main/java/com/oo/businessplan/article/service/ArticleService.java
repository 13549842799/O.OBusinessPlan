package com.oo.businessplan.article.service;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.mapper.ArticleMapper;
import com.oo.businessplan.article.pojo.AbstractArticle;
import com.oo.businessplan.basic.service.BaseService;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.pageModel.PageService;

/**
 * 文章类的相关接口
 * @author cyz
 *
 */
public interface ArticleService<T extends AbstractArticle<?>> extends BaseService<T>{
	
	/**
	 * 获取实现类的mapper属性
	 * @return
	 */
	ArticleMapper<T> getMapper();
	
	/**
	 * 通过标题模糊查询对应文章的文章列表 (id, title)
	 * @param title
	 * @return
	 */
	default List<T> searchTitle(String title) {
		return getMapper().searchTitle(title, DeleteFlag.VALID.getCode(), this.getClass().getSimpleName().replace("ServiceImpl", "").toLowerCase());
	}

	default PageInfo<T> getPage(T t, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = getMapper().getList(t);
		PageInfo<T> page = new PageInfo<>(list);
		return page;
	}
}
