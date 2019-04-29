package com.oo.businessplan.common.exception.handler;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.exception.AddErrorException;
import com.oo.businessplan.common.exception.AuthorityNotEnoughException;
import com.oo.businessplan.common.exception.login.LoginException;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.exception.mapper.ExceptionMapper;

/**
 * 全局异常监控处理
 * @author cyz
 *
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends BaseController{
	
	@Autowired
	ExceptionMapper em;
	
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
	
	/**
	 * 未处理异常
	 * @param request
	 * @param le
	 * @return
	 */
	@ExceptionHandler(value=Exception.class)
	public ResponseResult<Object> accidengExceptionHandler(HttpServletRequest request, Exception le) {
		le.printStackTrace();
		
		com.oo.businessplan.exception.pojo.Exception exp = 
				new com.oo.businessplan.exception.pojo.Exception
		  (currentAdminId(request), request.getRequestURI(), null, le.getMessage(), new Timestamp(new Date().getTime()));
			
		em.add(exp);
		
		return new ResponseResult<>().fail("网络延迟，请及时联系管理员");
	}
	
	/**
	 * 权限不足异常处理
	 * @param request
	 * @param le
	 * @return
	 */
	@ExceptionHandler(value=AuthorityNotEnoughException.class)
	public ResponseResult<Object> authNotEnoughExceptionHandler(HttpServletRequest request, AuthorityNotEnoughException le) {
		le.printStackTrace();
		
		return new ResponseResult<>().fail(le.getMessage());
	}
	
	/**
	 * 登录异常处理
	 * @param request
	 * @param le
	 * @return
	 */
	@ExceptionHandler(value=LoginException.class)
	public ResponseResult<Object> loginExceptionHandler(HttpServletRequest request, LoginException le) {
		le.printStackTrace();
		System.out.println("登录失效输出");
		return new ResponseResult<>().relogin();
	}

}
