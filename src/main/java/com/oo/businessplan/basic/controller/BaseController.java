package com.oo.businessplan.basic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.oo.businessplan.common.constant.EntityConstants;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.exception.login.LoginException;
import com.oo.businessplan.common.net.SessionInfo;
import com.oo.businessplan.common.redis.RedisTokenManager;
import com.oo.businessplan.common.security.SecurityAspect;
import com.oo.businessplan.common.security.SessionManager;
import com.oo.businessplan.common.util.HttpUtil;

public abstract class BaseController {
	
	@Autowired
    private SecurityAspect securityAspect;
	
	/**
	 * 通过header中的userName获取保存在redis中的sessionInfo对象
	 * @param request
	 * @return
	 * @throws ObjectNotExistException
	 */
	protected SessionInfo matchSessionInfo(HttpServletRequest request) {
		
		  String userCode = request
				  .getHeader(securityAspect.getUserCode());   //用户编号
		  SessionManager manager = securityAspect.getSessionManager();
		  SessionInfo info = 
				  manager.getSessionInfo(HttpUtil.getInstance().isPhoneLogin(request) ?
						  userCode + EntityConstants.REDIS_PHONE_SESSION_NAME : userCode + EntityConstants.REDIS_SESSION_NAME);//获取此用户维护redis的存储对象
	  
		  if (info==null) {
			      throw new LoginException();
		  }
		  return  info;
	}
	
	/**
	 * 获取当前用户的账号id
	 * @param request
	 * @return
	 * @throws ObjectNotExistException
	 */
	protected Integer currentAdminId(HttpServletRequest request)  {
		
		SessionInfo info = matchSessionInfo(request);
		
		return Integer.parseInt(String.valueOf(info.getId()));
	}
	
	/**
	 * 获取通过header传递过来的userName（accountName）
	 * @param request
	 * @return
	 */
	public String getAccountName(HttpServletRequest request){
		return request.getHeader(securityAspect.getUserCode());
	}


	public SecurityAspect getSecurityAspect() {
		return securityAspect;
	}


	public void setSecurityAspect(SecurityAspect securityAspect) {
		this.securityAspect = securityAspect;
	}
	
	

}
