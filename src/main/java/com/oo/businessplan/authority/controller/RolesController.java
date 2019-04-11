package com.oo.businessplan.authority.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.admin.service.AdminService;
import com.oo.businessplan.authority.pojo.AdminRole;
import com.oo.businessplan.authority.pojo.Role;
import com.oo.businessplan.authority.pojo.RolePage;
import com.oo.businessplan.authority.service.RoleService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.basic.service.CodeServie;
import com.oo.businessplan.common.constant.ResultConstant;
import com.oo.businessplan.common.constant.SystemKey;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.AddErrorException;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 增加角色接口
 * 修改角色接口
 * 删除角色接口
 * 给用户添加角色接口
 * 移动角色接口
 * 角色列表接口
 * 用户的角色列表接口
 * @author cyz
 *
 */
@RequestMapping("/api/authority/roles")
@Api("角色相关接口")
@RestController
public class RolesController extends BaseController{
	
	  @Autowired
	  private RoleService roleService;
	  
	  @Autowired
	  private AdminService adminService;
	  
	  @Resource(name="roleService")
	  private CodeServie cs;
	
	  @ApiOperation(value = "创建角色")
	  @RequestMapping(value="/add.do",method=RequestMethod.POST)
	  @IgnoreSecurity()
	  public ResponseResult<Object>  addRole(
			  HttpServletRequest request,
			  @RequestBody(required = true) Role role){
		  
		ResponseResult<Object> response = new ResponseResult<>();
		
		Integer currentAdmin = currentAdminId(request);
		role.setCreator(currentAdmin);
		role.setModifier(currentAdmin);
		
		if (StringUtil.isEmpty(role.getCode())) {
			role.setCode(cs.generalCode());
		} else if (cs.codeExists(role.getCode(), DeleteFlag.VALID)){
			return response.fail(ResultConstant.CODE_EXIST);		
		}	
		try {
			roleService.add(role);
			return response.success(role);
		} catch (AddErrorException e) {
			e.printStackTrace();
			return response.error(e.getMessage());
		}
	  }
	  
	  /**
	   * 
	   * @param request
	   * @param role
	   * @return
	   */
	  @ApiOperation(value = "编辑（修改）角色")
	  @RequestMapping(value="/edit.do",method=RequestMethod.PATCH)
	  @IgnoreSecurity()
	  public ResponseResult<Object>  editResource(
			  HttpServletRequest request,
			  @RequestBody(required = true) Role role){
		  
		ResponseResult<Object> response = new ResponseResult<>();
		
		if (role.getId() == 1) {
			return response.fail("超级管理员不能修改");
		}
		
		Role oldRode = roleService.getById(new Role(role.getId()));
		if (oldRode == null) {
			return response.fail(ResultConstant.NOT_EXIST_ROLE);
		}
		if (oldRode.getDelflag() == DeleteFlag.DELETE.getCode()) {
			return response.fail(ResultConstant.OBJECT_IS_DELETE);
		}
		if (StringUtil.isEmpty(role.getCode())) {
			return response.fail(ResultConstant.CODE_NOT_NULL);
		}
		if (!oldRode.getCode().equals(role.getCode()) 
				&& ((CodeServie)roleService).codeExists(role.getCode(), DeleteFlag.VALID)) {
			return response.fail(ResultConstant.CODE_EXIST);
		}

		role.setModifier(currentAdminId(request));
		role.setModifierTime(new Timestamp(new Date().getTime()));
		switch (roleService.updateFull(role)) {
		case 1:				
			return response.success(role);
		case 0:
			return response.fail("更新失败");
		case -1:
			return response.error("更新异常");
		}
		
		
		return response.success();
	  }
	  
	  @ApiOperation(value = "启用/禁用 角色")
	  @RequestMapping(value="/s/{id}/state.do",method=RequestMethod.PATCH)
	  @IgnoreSecurity()
	  public ResponseResult<Object> stateRole(
			  HttpServletRequest request,
			  @PathVariable("id")Integer id) {
		  ResponseResult<Object> response = new ResponseResult<>();		  
		  if (id == null) {
			return response.fail("请选择角色");
		  }		  		  
	      if (roleService.state(id, currentAdminId(request))) {
		      return response.success();
		  }
	  
		  return response.fail("操作失败,请联系管理员");
	  }
	  
	  @ApiOperation(value = "角色列表")
	  @RequestMapping(value="/list.re",method=RequestMethod.GET)
	  @IgnoreSecurity()
	  public ResponseResult<List<RolePage>> rolePageList(
			  HttpServletRequest request,
			  @ApiParam(value = "创建者", required = false)
			  @RequestParam(value="creator", required= false)Integer creator,
			  @ApiParam(value = "状态", required = false)
			  @RequestParam(value="state", required= false)Byte state) {
		  
		  ResponseResult<List<RolePage>> response = new ResponseResult<>();
		  RolePage param = new RolePage();
		  param.setState(state);
		  param.setDelflag(DeleteFlag.VALID.getCode());
		  return response.success(roleService.getFullList(param));
	  }
	  
	  @ApiOperation(value = "用户的角色列表")
	  @RequestMapping(value="/adminRoles.re",method=RequestMethod.GET)
	  @IgnoreSecurity()
	  public ResponseResult<List<Role>> getRolesFromAdmin(
			  HttpServletRequest request,
			  @ApiParam(value = "获取 拥有的/未拥有 的角色列表  参数  true/false", defaultValue= "true")
			  @RequestParam(value="type", required= false)boolean type,
			  @ApiParam(value = "用户id", required = true)
			  @RequestParam(value="adminId", required = true)int adminId) {
		  
		  ResponseResult<List<Role>> response = new ResponseResult<>();
		 
		  return response.success(roleService.getRolesOfAdmin(adminId, type));
	  }
	  
	  /**
	   * 首先查看用户是否存在
	   * 然后绑定角色到用户上
	   * @param request
	   * @param userId
	   * @param roleCodes
	   * @return
	   */
	  @ApiOperation("给用户添加角色")
	  @PostMapping(value="/addToUser.do")
	  @IgnoreSecurity()
	  public ResponseResult<List<Role>> addRoleToUser(
			  HttpServletRequest request,
			  @RequestBody List<AdminRole> adminrs) {
		  
		  ResponseResult<List<Role>> response = new ResponseResult<>();
          
		  Integer currentAdmin = currentAdminId(request);		  
		  for (AdminRole ar : adminrs) {
			if (ar.getDelflag() == 1) {
				ar.setCreator(currentAdmin);
				ar.setCreateTime(new Timestamp(new Date().getTime()));
			}
			ar.setModifier(currentAdmin);
		  }		  
		  roleService.insertOrUpdateRelation(adminrs);
		  //获取当前用户拥有角色
		  List<Role> adminRoles = roleService.getRolesOfAdmin(currentAdmin, true);			 
		  return response.success(adminRoles);
		  
	  }
	  
	  @ApiOperation("删除用户的指定角色")
	  @PostMapping(value="/delRelation.do")
	  @IgnoreSecurity()
	  public ResponseResult<Object> delflagFromUser(
			  HttpServletRequest request,
			  @ApiParam(value = "用户id", required = true)
			  @RequestParam(value="userId", required= true)Integer userId,
			  @ApiParam(value = "角色id数组", required = true)
			  @RequestParam(value="roleIds", required= true)int[] roleIds
			  ) {
		  ResponseResult<Object> response = new ResponseResult<>();
		  Admin admin = adminService.getById(new Admin(userId, DeleteFlag.VALID.getCode()));
		  if (admin == null) {
			return response.fail(ResultConstant.NOT_EXIST_ADMIN);
		  }
		  if (!roleService.removeRoleFromAdmin(userId, 
				roleIds, currentAdminId(request))) {
			return response.fail("删除失败");
		  }			
		  //获取当前用户拥有角色
		  List<Role> adminRoles = roleService.getRolesOfAdmin(userId, true);			 
		  return response.success(adminRoles);
		  
	  }
	  
	  @ApiOperation("移动角色")
	  @PatchMapping(value="/move.do")
	  @IgnoreSecurity()
	  public ResponseResult<List<Role>> movingRole(
			  HttpServletRequest request,
			  @RequestBody Role role) {
		  ResponseResult<List<Role>> response = new ResponseResult<>();
		  role.setDelflag(DeleteFlag.VALID.getCode());
		  if (roleService.getById(role) == null) {
			  return response.fail(ResultConstant.NOT_EXIST_ROLE);
		  }
		  List<Role> roles = roleService.updatePosition(role.getId(), role.getSort());
		  
		  return response.success(roles);
	  }
	  
}
