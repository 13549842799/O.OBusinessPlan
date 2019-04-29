package com.oo.businessplan.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oo.businessplan.article.pojo.entity.Diary;
import com.oo.businessplan.article.pojo.form.DiaryForm;
import com.oo.businessplan.basic.mapper.BaseMapper;

/**
 * 
 * @author cyz
 *
 */
public interface DiaryMapper extends BaseMapper<Diary>{
	
	/**
	 * 获取带有标签的日记列表
	 * @param form
	 * @return
	 */
	List<DiaryForm> getListWithLabels(DiaryForm form);

	/**
	 * 从数据库中获取日记和关联的数据
	 * @param id
	 * @param code
	 * @return
	 */
	DiaryForm getCompleteDiaryById(@Param("id")int id, @Param("delflag")byte delflag);
	
	

}
