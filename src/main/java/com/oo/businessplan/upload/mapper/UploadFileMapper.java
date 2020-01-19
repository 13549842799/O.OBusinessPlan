package com.oo.businessplan.upload.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.basic.mapper.BaseMapper;
import com.oo.businessplan.upload.pojo.UploadFile;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-14 10:23:08
 */
public interface UploadFileMapper extends BaseMapper<UploadFile> {
	
	@Delete("delete from upload where id in (${ids}) and creator = #{creator}")
	int deleteBatch(@Param("ids")String ids, @Param("creator") Integer creator);
	
	/**
	 * 删除临时文件，这个文件必须是还没有与对应的实体（例如，小说，章节等）关联的
	 * @param id
	 * @param creator
	 * @return
	 */
	@Delete("delete from upload where id = #{id} and creator = #{creator} AND objId is null")
	int deleteReal(@Param("id")long id, @Param("creator") Integer creator);
	
	@Select("select path from upload where id in (${ids}) and creator = #{creator}")
	String[] getFilesPath(@Param("ids")String ids, @Param("creator")Integer creator);

	@Update("update upload set objId = #{objId} where id in (${ids})")
	int updateObjId(@Param("objId")long objId, @Param("ids")String ids);
	
	@Select("select id from upload where id = #{id} and creator = #{creator} and relevance = #{relevance}")
	Long checkFileExist(UploadFile file);
	
	/**
	 * 获取某个实体关联的文件
	 * @param file
	 * @return
	 */
	@Select("select id, path from upload where creator = #{creator} and objId = #{objId} and relevance = #{relevance}")
	List<UploadFile> checkFileExistInObj(UploadFile file);
}