package com.oo.businessplan.article.mapper;

import com.oo.businessplan.basic.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.article.pojo.entity.Section;
import com.oo.businessplan.article.pojo.form.SectionForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-20 15:55:56
 */
public interface SectionMapper extends BaseMapper<Section> {
	
	/**
	 * 如果section中id为-1表示查找当前小说的第一章
	 * @param section
	 * @return
	 */
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
	
	
	/*Section laststSection(Section section);*/
	
	/**
	 * 获取当前章节的上一章节
	 * 存在一下情形:
	 * 1.没有上一章：  
	 *     当前章节为第一卷第一章
	 * 2.有上一章：
	 *     上一章为同分卷
	 *     上一章为上一个分卷的最后一章
	 * @param current
	 * @return
	 */
	Long lastSectionId(Section current);
	
	/**
	 * 获取当前章节的下一章节
	 * 存在以下情形：
	 * 1.没有下一章节
	 * 2。下一章节为下一分卷的第一章
	 * @param current
	 * @return
	 */
	Long nextSectionId(Section current);
}