package com.oo.businessplan.article.service;


import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.pojo.entity.Diary;
import com.oo.businessplan.article.pojo.form.DiaryForm;
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
	PageInfo<DiaryForm> getPage(DiaryForm form);

	/**
	 * 通过日记id获取完整的日记（比如包含着标签列表等）
	 * @param id
	 * @return
	 */
	DiaryForm getCompleteDiary(int id);

}
