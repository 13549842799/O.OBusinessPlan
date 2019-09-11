package com.oo.businessplan.article.service;

import com.oo.businessplan.basic.service.BaseService;

import java.util.List;

import com.oo.businessplan.article.pojo.entity.Section;
import com.oo.businessplan.article.pojo.form.SectionForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-20 15:55:56
 */
public interface SectionService extends BaseService<Section> {
	
	SectionForm getExpandSection(Section section);

	List<Section> getSimpleSections(Section section);
	
	/**
	 * 
	 * @param current <id, novelId, portionId, number>
	 * @return
	 */
	Long lastSectionId(Section current);
	
	/**
	 * 插入最新章的下一个章节,也就是下一卷的第一个章节
	 * @param current
	 * @return
	 */
	Long nextSectionId(Section current);
	
}