package com.oo.businessplan.upload.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.oo.businessplan.basic.mapper.BaseMapper;
import com.oo.businessplan.upload.pojo.UploadFile;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-14 10:23:08
 */
public interface UploadFileMapper extends BaseMapper<UploadFile> {
	
	@Delete("delete from upload where id in (#{ids}) and creator = #{creator}")
	int deleteBatch(@Param("ids")String ids, @Param("creator") Integer creator);
	
	@Select("select path from upload where id in (#{ids}) and creator = #{creator}")
	String[] getFilesPath(@Param("ids")String ids, @Param("creator")Integer creator);
}