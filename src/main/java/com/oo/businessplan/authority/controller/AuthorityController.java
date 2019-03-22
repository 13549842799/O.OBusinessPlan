package com.oo.businessplan.authority.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.AuthorityWithKey;
import com.oo.businessplan.authority.pojo.Resource;
import com.oo.businessplan.authority.service.AuthorityService;
import com.oo.businessplan.authority.service.ResourceService;
import com.oo.businessplan.authority.service.RoleService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.basic.service.UtilService;
import com.oo.businessplan.common.constant.ResultConstant;
import com.oo.businessplan.common.constant.SystemKey;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.enumeration.StatusFlag;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.net.SessionInfo;
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
	private UtilService<Authority> util;
	
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
	
	@ApiOperation("为角色添加权限")
	@PostMapping(value="/add.do")
	@IgnoreSecurity()
	public ResponseResult<Authority> addAuthority(
			HttpServletRequest request,
			/*@ApiParam(value="角色id")
			@RequestParam(value="roleId", required=true)int roleId,
			@ApiParam(value="资源id数组")
			@RequestParam(value="resourceIds", required=true)int[] resourceIds,
			@ApiParam(value="类型数组 与上面的参数一一对应")
			@RequestParam(value="types", required=true)int[] types,*/
			@ApiParam(value="权限数组参数")
			@RequestBody(required = true) Authority[] auths) {
		ResponseResult<Authority> response = new ResponseResult<>();
		
		/*if (resourceIds == null || resourceIds.length == 0 
				|| types == null || types.length == 0 || resourceIds.length != types.length) {
			return response.error("请求参数错误");
		}*/
		
		if (auths.length == 0) {
			return response.error("请求参数错误");
		}
		
		List<Authority> contain = new ArrayList<>();
		
		int[] resourceIds = new int[auths.length];	
		int roleId = auths[0].getRoid();
		for (int i = 0; i < auths.length; i++) {
			resourceIds[i] = auths[i].getReid();
		}
		
		List<Authority> list = authService.getAuthorities(roleId, resourceIds);
		Authority temp = null;
		if (list != null && list.size() > 0) {
			Authority keyParam = new Authority(roleId);
			Map<String, Authority> map = util.createMapFromList(list);
			for (int i = 0; i < resourceIds.length; i++) {
				keyParam.setReid(resourceIds[i]);
				if ((temp = map.get(keyParam)) != null) {
					temp.setDelflag(DeleteFlag.VALID.getCode());
					temp.setType((byte)auths[i].getType());
				} else {
					temp = new Authority(roleId, resourceIds[i], (byte)auths[i].getType(), 
							DeleteFlag.VALID.getCode());
				}
				contain.add(temp);
			}
		}
		
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
		
        if (authService.delete(auth.getId())) {
			return response.success();
		}
        return response.error(SystemKey.ERROR_KEY);
	}
	
}
