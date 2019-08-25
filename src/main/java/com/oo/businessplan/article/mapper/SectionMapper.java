package com.oo.businessplan.article.mapper;

import com.oo.businessplan.basic.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.oo.businessplan.article.pojo.entity.Section;
import com.oo.businessplan.article.pojo.form.SectionForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-20 15:55:56
 */
public interface SectionMapper extends BaseMapper<Section> {
	
	
	SectionForm getExpandSection(Section section);
	
	/**
	 * 把在section表中删除的数据备份在section_del表中
	 * @param section
	 */
	void addToDel(Section section);
	
	@Select("select id, title, portionId, wordsNum from section where portionId = ifnull(#{portionId}, portionId)")
	List<Section> getSimpleSections(Section section);
	
	@Select("select ifnull(wordsNum, 0) from section where id = #{id}")
	int sectionNum(@Param("id")long id);
}