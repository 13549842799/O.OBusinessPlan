package com.oo.businessplan.admin.mapper;

import com.oo.businessplan.basic.mapper.BaseMapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.oo.businessplan.admin.pojo.entity.AccountManager;
import com.oo.businessplan.admin.pojo.entity.Key;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-07 17:28:11
 */
public interface AccountManagerMapper extends BaseMapper<AccountManager> {

	@Insert("insert into accountKeyManager value(#{accountId}, #{secretkey})")
	void insertKey(Key key);

	@Select("select secretkey from accountKeyManager where accountId = #{adminId}")
	String getKey(int adminId);
}