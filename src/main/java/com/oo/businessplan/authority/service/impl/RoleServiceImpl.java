package com.oo.businessplan.authority.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.oo.businessplan.authority.mapper.RoleMapper;
import com.oo.businessplan.authority.pojo.AdminRole;
import com.oo.businessplan.authority.pojo.Role;
import com.oo.businessplan.authority.pojo.RolePage;
import com.oo.businessplan.authority.service.RoleService;
import com.oo.businessplan.basic.service.CodeServie;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.enumeration.StatusFlag;
import com.oo.businessplan.common.exception.UpdateErrorException;
import com.oo.businessplan.common.exception.UpdateErrorException.ErrorType;
import com.oo.businessplan.common.pageModel.MethodResult;
import com.oo.businessplan.common.redis.RedisTokenManager;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService, CodeServie {
	
	
	@Autowired
	RoleMapper roleMapper;
	
	@Autowired
	RedisTokenManager tokenManager;

	@Override
	@Transactional
	public MethodResult<String> giveRole(int[] roleIds, int adminId, Integer creator) {	
		MethodResult<String> result = new MethodResult<>();
		  try {
		    roleMapper.giveRolesBatch(roleIds, adminId, creator);
		    return result.success();
		  } catch (SQLException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return result.fail("角色赋予重复");
		  }
	}

	@Override
	public List<Role> getRolesOfAdmin(int userId, boolean type) {
		
		return roleMapper.getRolesOfAdmin(userId, type,
				DeleteFlag.VALID.getCode(), StatusFlag.ENABLE.getCode());
	}

	@Override
	public boolean codeExists(String code, DeleteFlag delflag) {
		return roleMapper.checkCodeExists(code) > 0;
	}

	@Override
	public String generalCode() {
		
		return roleMapper.getCode(5);
	}

	@Override
	public boolean removeRoleFromAdmin(int adminId, int[] roleIds, Integer modifier) {
		Map<String, Object> params = new HashMap<>();
		params.put("adminId", adminId);
		params.put("roleIds", roleIds);
		params.put("modifier", modifier);
		params.put("delflag", DeleteFlag.DELETE.getCode());
		int result = roleMapper.deleteFromAdmin(params);
		return result == roleIds.length;
	}

	@Override
	@Transactional
	public List<Role> updatePosition(int roleId, int newPosition) {
		Role role = new Role();
		role.setId(roleId);
		role.setSort(newPosition);
		role.setDelflag(DeleteFlag.VALID.getCode());
		int count = roleMapper.update(role);
		if (count != 1) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		roleMapper.movebackOneposition(roleId, newPosition, DeleteFlag.VALID.getCode());
		
		return roleMapper.getList(role);
	}

	@Override
	public List<RolePage> getFullList(RolePage param) {
		return roleMapper.getFullList(param);
	}

	@Override
	@Transactional()
	public void insertOrUpdateRelation(List<AdminRole> ar) {		
		try {
			roleMapper.insertOrUpdate(ar);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new UpdateErrorException(ErrorType.INSERT_OR_UPDATE_ERROR);
		}
	}
	
	
    
	
	
}
