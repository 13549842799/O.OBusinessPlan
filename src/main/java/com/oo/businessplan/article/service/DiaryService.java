package com.oo.businessplan.article.service;


import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.pojo.entity.Diary;
import com.oo.businessplan.basic.service.BaseService;

/**
 * 
 * @author cyz
 *
 */
public interface DiaryService extends BaseService<Diary>{
	
	int pageSize = 10;
	
	/**
	 * 
	 * @param pageNum
	 * @return
	 */
	PageInfo<Diary> getPage(Integer pageNum, Integer adminId, Integer classify);

}
