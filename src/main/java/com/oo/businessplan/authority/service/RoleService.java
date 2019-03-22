package com.oo.businessplan.authority.service;

import java.util.List;
import java.util.Map;

import com.oo.businessplan.authority.pojo.Role;
import com.oo.businessplan.authority.pojo.RolePage;
import com.oo.businessplan.basic.service.BaseService;

public interface RoleService extends BaseService<Role>{
	
	/**
	 * 赋予角色
	 * @param roleIds 赋予的角色id组
	 * @param adminId 被赋予角色的账号
	 * @param creator 创建人id（admin）
	 */
	Map<String,String> giveRole(int[] roleIds,int adminId,Integer creator);
    
	/**
	 * 获取对应用户所拥有的角色(角色必须未被删除和必须是启用状态的)，
	 * @param userId
	 * @return
	 */
	List<Role> getRolesOfAdmin(int userId, boolean type);

	/**
	 * 从用户中移除指定的角色
	 * @param adminId
	 * @param roleIds
	 * @param modifier
	 */
	boolean removeRoleFromAdmin(int adminId, int[] roleIds, Integer modifier);
	
	/**
	 * 更改顺序
	 * @param roleId
	 * @param newPositino
	 */
	List<Role> updatePosition(int roleId, int newPosition);

	List<RolePage> getFullList(RolePage param);

}
