package com.oo.businessplan.article.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.oo.businessplan.article.pojo.AbstractArticle;
import com.oo.businessplan.basic.mapper.BaseMapper;

/**
 * 文章类的接口
 * @author cyz
 *
 * @param <T>
 */
public interface ArticleMapper<T extends AbstractArticle<?>> extends BaseMapper<T>{

	/**
	 * 根据关键字模糊查询标题
	 * @param name
	 * @param delflag
	 * @param table
	 * @return
	 */
	@Select("SELECT id, title FROM ${table} WHERE creator = IFNULL(#{creator}, creator) AND LOCATE(#{name}, title) > 0 AND delflag = #{delflag}")
	List<T> searchTitle(@Param("name")String name, @Param("creator")Integer creator, @Param("delflag")byte delflag, @Param("table")String table);
	
	
}
