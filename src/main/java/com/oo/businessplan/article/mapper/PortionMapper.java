package com.oo.businessplan.article.mapper;

import com.oo.businessplan.basic.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.article.pojo.entity.Portion;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-20 10:21:41
 */
public interface PortionMapper extends BaseMapper<Portion> {
	
	 /**
	  * 获取拓展的列表
	  * @param portion
	  * @return
	  */
	 List<Portion> getExpandList(Portion portion);
	 
	 @Select("select count(id) from portion where novelId = #{novelId} and delflag = #{delflag}")
	 int getNovelPortionNum(@Param("novelId")int novelId, @Param("delflag")byte delflag);
	 
	 @Select("select ifnull(max(number),0) from portion where novelId = #{novelId} and delflag = #{delflag}")
	 int getNovelMaxPortion(@Param("novelId")int novelId, @Param("delflag")byte delflag);
	 
	 /**
	  * 维护字数和章节数
	  * @param id
	  * @param sNum
	  * @param wordNums
	  * @return
	  */
	 @Update("update portion set sectionNum = sectionNum + #{sectionsNum}, wordsNum = wordsNum + #{wordNums} where id = #{id}")
	 int containPortionNum(@Param("id")int id, @Param("sectionsNum")Integer sNum,  @Param("wordNums")Integer wordNums);
	 
}