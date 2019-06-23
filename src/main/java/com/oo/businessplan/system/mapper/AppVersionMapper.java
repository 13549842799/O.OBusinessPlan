package com.oo.businessplan.system.mapper;

import org.apache.ibatis.annotations.Select;

import com.oo.businessplan.basic.mapper.BaseMapper;
import com.oo.businessplan.system.pojo.AppVersion;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-06-22 20:57:31
 */
public interface AppVersionMapper extends BaseMapper<AppVersion> {

	/**
	 * 获取数据库中app最后一个版本的版本号
	 * @param delflag
	 * @return
	 */
	@Select("SELECT versionNo FROM app_version WHERE delflag = #{delflag, jdbcType=TINYINT} ORDER BY createTime DESC LIMIT 1")
	String getNewstWersionNo(byte delflag);

	/**
	 * 获取数据库中app版本最新的一条记录
	 * @return
	 */
	@Select("SELECT * FROM app_version WHERE delflag = #{delflag, jdbcType=TINYINT} ORDER BY createTime DESC LIMIT 1")
	AppVersion getNewstVersion();
}