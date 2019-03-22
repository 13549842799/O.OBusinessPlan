package com.oo.businessplan.authority.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.Role;
import com.oo.businessplan.authority.pojo.RolePage;
import com.oo.businessplan.basic.mapper.BaseMapper;

public interface RoleMapper extends BaseMapper<Role>{
	
	/**
	 * 
	 * @param roleIds
	 * @param adminId
	 * @param creator
	 * @throws SQLException
	 */
    void giveRolesBatch(@Param("roleIds")int[] roleIds,@Param("adminId")int adminId,@Param("creator")Integer creator) throws SQLException;
    
    /**
     * 
     * @param authorities
     * @return
     */
    int addRelationBetweenAdminAndRole(Authority[] authorities);

    /**
     * 
     * @param userId
     * @param code
     * @param code2
     * @return
     */
	List<Role> getRolesOfAdmin(@Param("adminId")Integer userId, @Param("type")boolean type,
			@Param("delflag")byte delflag, @Param("state")byte state);

	/**
	 * 
	 * @param code
	 * @param delflag
	 * @param state
	 * @return
	 */
	@Select("SELECT COUNT(0) FROM role WHERE TRIM(`code`) = TRIM(#{code}) ")
	int checkCodeExists(@Param("code")String code);
	
	/**
	 * 
	 * @param length
	 * @return
	 */
	@Select("SELECT LPAD(COUNT(0)+1, #{length}, '0') FROM role ")
	String getCode(@Param("length")int length);

	/**
	 * 删除数据库中用户与角色对应的关联
	 * @param params adminId<int>, roleIds<int[]>, modifier<int>, delflag<byte>
	 */
	int deleteFromAdmin(Map<String, Object> params);
	
	/**
	 * 在位置为node的角色的后面所有角色都移动一位
	 * @param curId 不包括这个id的角色,可不传
	 * @param node
	 * @param delflag
	 */
	@Update("<script> UPDATE role SET sort = sort + 1 WHERE delflag = #{delflag} "
			+ "<if test='id != null'> AND id != #{id}</if> </script>")
	void movebackOneposition(@Param("id")Integer curId, @Param("sort")int node, @Param("delflag")byte delflag);

	List<RolePage> getFullList(RolePage param);

}
