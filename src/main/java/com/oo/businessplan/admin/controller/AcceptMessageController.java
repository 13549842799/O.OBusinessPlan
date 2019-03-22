package com.oo.businessplan.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.admin.service.AcceptMessageService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RequestMapping("/api/acceptMessage")
@Api("消息接收接口模块")
@RestController
public class AcceptMessageController extends BaseController {
	
	
	@Autowired
	AcceptMessageService amService;
	
	/**
	 * 
	 * @param request
	 * @param type
	 * @param state
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/{type}/list.do")
	@ApiOperation(value = "获取当前登陆对象完整消息的集合")
	@IgnoreSecurity(authority = false)
	public ResponseResult<List<Map<String,Object>>>  getMessageListByType(
			HttpServletRequest request,
			@PathVariable(value="type", required=true)Byte type,
			@ApiParam(value = "状态id", required = true)  @RequestParam(required=false,value="state")Byte state,
			@ApiParam(value = "页数", required = false)  @RequestParam(required=false,value="pageNum",defaultValue="1")Integer pageNum,
			@ApiParam(value = "页容量", required = false)  @RequestParam(required=false,value="pageSize",defaultValue="10")Integer pageSize
			){
		
		ResponseResult<List<Map<String,Object>>> response = new ResponseResult<>();
		
		String account = getAccountName(request);
		
		List<Map<String,Object>> list = amService.getAdminMessage(account, type, 
				state, pageNum, pageSize);
		
		return response.success(list);
	}
	
	
	

}
