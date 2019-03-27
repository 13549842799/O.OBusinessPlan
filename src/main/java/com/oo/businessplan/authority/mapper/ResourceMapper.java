package com.oo.businessplan.authority.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.oo.businessplan.authority.pojo.Resource;
import com.oo.businessplan.basic.mapper.RedisCacheMapper;


/**
 * 
 * @author cyz
 * @version 创建时间：2018-08-10 22:05:41
 */
public interface ResourceMapper extends RedisCacheMapper<Resource> {
	
	/**
	 * 通过账号的id获取此账号下所有权限关联的资源
	 * admin -> admin_role -> role -> authority_role -> authority -> resource
	 * @param aid  账号id
	 * @param delflag
	 * @param state
	 * @return
	 */
    List<Resource>	getResourcesForUser(@Param("aid")Integer aid,
    		    @Param("delflag")short delflag,@Param("state")short state);
    
    /**
     * 通过权限id获取资源列表
     * @param auid
     * @param delflag
     * @param state
     * @return
     */
    @Select("SELECT * FROM resource WHERE auId = #{auid} AND "
    		+ "delflag = #{delflag,jdbcType=TINYINT} AND state = #{state,jdbcType=TINYINT}")
    List<Resource>  getResourcesForAuthority(@Param("auid")Integer auid,@Param("delflag")short delflag,@Param("state")short state);

    /**
	   * 获取admin可访问的资源
	   * @param adminId
	   * @param adminCode
	   * @param delflag
	   * @param state
	   * @return
	   */
    List<Resource> getModuleResourcesList(@Param("adminId")Integer adminId,
    		@Param("adminCode")String adminCode,@Param("type")int type,@Param("delflag")byte delflag,
    		@Param("state")byte state);
    
  
}