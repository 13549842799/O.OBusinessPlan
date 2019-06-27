package com.oo.businessplan.additional.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.additional.pojo.Msg;
import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.admin.service.AdminService;
import com.oo.businessplan.basic.service.MsgService;

import com.oo.businessplan.common.pageModel.ResponseResult;

import com.oo.businessplan.common.util.StringUtil;

/**
 * 短信controller
 * 本系统需要发送短信的功能是 手机绑定 和 密码修改
 * 什么功能需要发送短信
 * 发送怎样的短信
 * 如何发送短信
 * 
 * 短信分为 验证码短信 和 内容短信  其中验证码短信又分为 密码验证码短信  和 手机验证码短信
 * @author cyz
 *
 */
@RestController
@RequestMapping("/api/msg")
public class MsgController {
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private MsgService msgService;
	
	/**
	 * 发送短信
	 * 本接口无需权限判断
	 * 向对应用户名的用户的绑定手机发送验证码
	 * 验证码保存在redis中
	 * 步骤： 
	 * 1.验证用户是否存在
	 * 2.验证手机是否绑定
	 * 3.生成短信实体
	 * 4.调用接口发送短信
	 * 5.如果短信发送成功则根据短信类型设置过期时间，保存在redis中。
	 * @param request
	 * @param accountName 用户名
	 * @param type 1-绑定手机  2-修改密码
	 * @return
	 */
	@PostMapping("/{type}/{accountName}")
	public ResponseResult<String> sendMsg(HttpServletRequest request, @PathVariable(value="accountName") String accountName, @PathVariable(value="type") byte type) {
		
		ResponseResult<String> response = new ResponseResult<>();
		
		Admin admin = adminService.getAdminByAccountName(accountName);
		String phone = admin.getBindPhone();
		if (StringUtil.isEmpty(phone)) {
			return response.fail("没有绑定手机");
		}
		Msg message = msgService.generateMsg(phone, type);
		
		if (!msgService.sendMeg(message)) {
			return response.fail("短信发送失败");
		}
		
		return response.success();
	}
	
}
