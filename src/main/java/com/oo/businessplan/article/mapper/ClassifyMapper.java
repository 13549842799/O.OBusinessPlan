package com.oo.businessplan.article.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.article.pojo.entity.Classify;
import com.oo.businessplan.basic.mapper.BaseMapper;

/**
 * 注意：当使用分类表和文章类型的表进行关联时，要区分系统分类和自定义分类这两种情况。
 * 因为系统表是所有用户公用的，所以在查询文章时要加上文章的创建人这个条件来进行区分，否则会把系统分类下的所有文章都查询出来
 * @author tangdabing
 *
 */
public interface ClassifyMapper extends BaseMapper<Classify> {
	
	/**
	 * 检查目标类型下存在的文章数
	 * @param table 表名
	 * @param id 分类id
	 * @param delflag 删除标志
	 * @return
	 */
	@Select("SELECT COUNT(0) FROM classify cl LEFT JOIN ${table} t ON t.classify=cl.id WHERE t.delflag=#{delflag, jdbcType=TINYINT} AND t.creator = #{creator}")
	int checkTheClassifyArticleCount(@Param("table")String table,@Param("id") int id,@Param("creator") int creator, @Param("delflag") byte delflag);
	
	/**
	 * 更新对应类型文章的分类
	 * @param table
	 * @param creator 对应文章的拥有人
	 * @param oldClassifyId
	 * @param newClassifyId
	 */
	@Update("UPDATE ${table} SET classify = #{newId} WHERE classify=#{oldClassifyId} AND creator = #{creator}")
	void moveArticle(@Param("table")String table,@Param("creator") int creator,@Param("oldClassifyId") int oldClassifyId, @Param("newId") int newClassifyId);

	/**
	 * 通过条件获取分类
	 * @param cls
	 * @return
	 */
	@Select("SELECT * FROM classify WHERE name = #{name} AND delflag = #{delflag} AND childType = #{childType}")
	Classify getUnique(Classify cls);

	@Select("SELECT COUNT(0) FROM classify WHERE name = #{name} AND delflag = #{delflag,jdbcType=TINYINT} AND (type = #{type,jdbcType=TINYINT} OR (childType = #{childType, jdbcType=TINYINT} AND creator = #{creator}))")
	int count(@Param("name")String name, @Param("type")byte type, @Param("childType")byte childType, @Param("creator")Integer creator,@Param("delflag")byte delflag);
}
