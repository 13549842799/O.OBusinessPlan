package com.oo.businessplan.article.mapper;

import com.oo.businessplan.basic.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.article.pojo.entity.Novel;
import com.oo.businessplan.article.pojo.form.NovelForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-03-28 14:32:47
 */
public interface NovelMapper extends BaseMapper<Novel> {
	
	/**
	 * 查询拓展的小说列表,增加了作者昵称，标签等，增加了查询的参数
	 * @param form
	 * @return
	 */
	List<NovelForm> getExpandList(NovelForm form);
	
	/**
	 * 
	 * @param novel
	 * @return
	 */
	NovelForm getComplete(Novel novel);
	
	/**
	 * 
	 * @param id
	 * @param num
	 */
	@Update("update novel set portionsNum = portionsNum + #{num} where id = #{id}")
	void updatePortionNum(@Param("id")int id, @Param("num")int num);
	
	@Update("update novel set wordsNum = wordsNum + #{wordNum} where id = (select novelId from portion where id = #{portionId})")
	void updateWordNums(@Param("portionId")int portionId, @Param("wordNum")int wordNums);
	
}