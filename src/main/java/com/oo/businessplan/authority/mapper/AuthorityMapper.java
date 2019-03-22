package com.oo.businessplan.authority.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.AuthorityWithKey;
import com.oo.businessplan.basic.mapper.BaseMapper;

public interface AuthorityMapper extends BaseMapper<Authority>{
	
	/**
    * 根据账号用户名获取有效的权限列表
    * @param accountName
    * @param delflag
    * @param state
    * @return
    */
   List<Authority> getAuthoritiesByAccount(@Param("accountName")String accountName,@Param("delflag")byte delflag);

   /**
    * 根据条件获取对应的权限列表
    * @param params roId<int,角色id>,reIds<int[],资源id数组>,delflag<Byte,删除标志>
    * @return
    */
   List<Authority> getAuthorities(Map<String, Object> params);

   void insertOrUpdate(List<Authority> list);

   List<AuthorityWithKey> getAuthorityByRole(@Param("roleId")int roleId, 
		   @Param("delflag")byte delflag);

}
