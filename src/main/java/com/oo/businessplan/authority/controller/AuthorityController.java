package com.oo.businessplan.authority.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.AuthorityWithKey;
import com.oo.businessplan.authority.pojo.Resource;
import com.oo.businessplan.authority.pojo.ResourceAuths;
import com.oo.businessplan.authority.service.AuthorityService;
import com.oo.businessplan.authority.service.ResourceService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.constant.ResultConstant;
import com.oo.businessplan.common.constant.SystemKey;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/authority/auth")
@Api("权限模块接口")
public class AuthorityController extends BaseController{
	
	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private ResourceService resourceService;
	
	@ApiOperation(value = "获取树形格式的资源携带权限的列表")
	  @RequestMapping(value="/tree.re",method=RequestMethod.GET)
	  @IgnoreSecurity()
	  public ResponseResult<Object>  treeResources(
		HttpServletRequest request,
		@RequestParam(value="roleId", required=true)int roleId){
		  	  
		ResponseResult<Object> response = new ResponseResult<>();
		Queue<Resource> queue = new LinkedList<>(resourceService.getFullList(roleId));
		List<Resource> list = resourceService.getResourceTree(queue, null);
		list.forEach(o->System.out.println(o));
		return response.success(list);	  
	  }
	
	@ApiOperation("获取角色拥有的权限列表")
	@GetMapping(value="/list.re")
	@IgnoreSecurity()
	public ResponseResult<List<AuthorityWithKey>> authList(
			HttpServletRequest request, 
			@ApiParam(value="角色id")
			@RequestParam(value="roleId", required=true)int roleId) {
		ResponseResult<List<AuthorityWithKey>> response = new ResponseResult<>();
		
		List<AuthorityWithKey> list = authService.getfullList(roleId);
		
		
		return response.success(list);
	}
	
	/**
	 * 为角色添加权限
	 * @param request
	 * @param auths
	 * @return
	 */
	@PostMapping(value="/add.do")
	@IgnoreSecurity()
	public ResponseResult<Authority> addAuthority(
			HttpServletRequest request,
			@RequestBody(required = true) Authority[] auths) {
		ResponseResult<Authority> response = new ResponseResult<>();
		
		if (auths.length == 0) {
			return response.error(ResultConstant.PARAMETER_ERROR);
		}
		
		/*
		 * 查找当前用户拥有的可授予类型的权限，然后判断要新增或删除的权限是否在这些可授予权限内
		 */
		List<Authority> adminAuths = authService.getListByAccountAndType(currentAdminId(request), Authority.AWARD);
		
		if (adminAuths == null || adminAuths.size() == 0) {
			return response.fail("没有可授予的权限");
		}
		
		Map<Integer, List<Authority>> authMap = adminAuths.stream().collect(Collectors.groupingBy(Authority::getReid));
		
		List<Authority> contain = new ArrayList<>();	
		
		List<Authority> tempList = null;
		for (int i=0; i < auths.length; i++) {
			tempList = authMap.get(auths[i].getReid());
			if (tempList != null && tempList.size() > 0) {
				contain.add(auths[i]);
			}
		 }
		 contain.forEach(o->System.out.println(o));
		 if (authService.insertOrUpdateForBatch(contain)) {
			 return response.success();
		 }		
		 return response.error("网络异常");
	}
	
	@ApiOperation("为角色修改权限")
	@PatchMapping(value="/edit.do")
	@IgnoreSecurity()
	public ResponseResult<Authority> updateAuthority(
			HttpServletRequest request,
			@RequestBody(required = true) Authority auth) {
		
		ResponseResult<Authority> response = new ResponseResult<>();
		
		auth.setDelflag(DeleteFlag.VALID.getCode());
		switch (authService.update(auth)) {
		case 1:
			return response.success(auth);
		case 0:
			return response.fail(ResultConstant.NOT_EXIST_AUTH);
		default :
			return response.error(SystemKey.ERROR_KEY);
		}
	}
	
	@ApiOperation("删除角色的某个权限")
	@PatchMapping(value="/delete.do")
	@IgnoreSecurity()
	public ResponseResult<Authority> deleteAuthority(
			HttpServletRequest request,
			@RequestBody(required = true) Authority auth) {
		
		ResponseResult<Authority> response = new ResponseResult<>();
        if (authService.delete(auth)) {
			return response.success();
		}
        return response.error(SystemKey.ERROR_KEY);
	}
	
}
