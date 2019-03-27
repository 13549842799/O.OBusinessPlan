package com.oo.businessplan.common.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oo.businessplan.common.exception.AddErrorException;
import com.oo.businessplan.common.exception.login.LoginException;
import com.oo.businessplan.common.pageModel.ResponseResult;

/**
 * 全局异常监控处理
 * @author cyz
 *
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
	/**
	 * 登录异常处理
	 * @param request
	 * @param le
	 * @return
	 */
	@ExceptionHandler(value=LoginException.class)
	public ResponseResult<Object> reloginHandler(HttpServletRequest request, LoginException le) {
		le.printStackTrace();
		return new ResponseResult<>().relogin();
	}
	
	/**
	 * 新增记录异常处理
	 * @param request
	 * @param le
	 * @return
	 */
	@ExceptionHandler(value=AddErrorException.class)
	public ResponseResult<Object> addObjectHandler(HttpServletRequest request, AddErrorException le) {
		le.printStackTrace();
		return new ResponseResult<>().fail(le.getMessage());
	}

}
