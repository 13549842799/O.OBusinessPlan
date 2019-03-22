package com.oo.businessplan.basic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.oo.businessplan.common.constant.EntityConstants;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.net.SessionInfo;
import com.oo.businessplan.common.redis.RedisTokenManager;
import com.oo.businessplan.common.security.SecurityAspect;

public abstract class BaseController {
	
	@Autowired
    private SecurityAspect securityAspect;
	
	/**
	 * 通过header中的userName获取保存在redis中的sessionInfo对象
	 * @param request
	 * @return
	 * @throws ObjectNotExistException
	 */
	protected SessionInfo matchSessionInfo(HttpServletRequest request) throws ObjectNotExistException{
		
		  String userCode = request
				  .getHeader(securityAspect.getUserCode());   //用户编号
		  
		  RedisTokenManager tokenManager =
				  securityAspect.getTokenManager();
		  SessionInfo info = (SessionInfo)tokenManager//获取此用户维护redis的存储对象
				  .getValueFromMap(userCode,EntityConstants.REDIS_SESSION_NAME);		  
		  if (info==null) {
			      throw new ObjectNotExistException("登陆失效");
		  }
		  return  info;
	}
	
	/**
	 * 获取当前用户的账号id
	 * @param request
	 * @return
	 * @throws ObjectNotExistException
	 */
	protected Integer currentAdminId(HttpServletRequest request) throws ObjectNotExistException {
		
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
