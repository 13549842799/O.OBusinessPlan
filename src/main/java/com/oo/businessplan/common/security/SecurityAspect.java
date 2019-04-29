package com.oo.businessplan.common.security;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.oo.businessplan.authority.service.AuthorityService;
import com.oo.businessplan.common.constant.EntityConstants;
import com.oo.businessplan.common.exception.AuthorityNotEnoughException;
import com.oo.businessplan.common.exception.login.LoginException;
import com.oo.businessplan.common.exception.login.TokenException;
import com.oo.businessplan.common.net.SessionInfo;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.redis.RedisTokenManager;
import com.oo.businessplan.common.util.StringUtil;

public class SecurityAspect {
	
	 private static final String DEFAULT_TOKEN_NAME = "User-Token";

     private static final String DEFAULT_USER_NAME = "User-Name"; 
     
     private RedisTokenManager tokenManager;
	
	 private String tokenName;
	 
	 private String userCode;
	 
	 @Autowired
	 private AuthorityService authorityService;
	 
	 
	 public Object execute(ProceedingJoinPoint pg) throws Throwable{
		 
		 //从切点上获取目标方法
		 MethodSignature  methodSignature =(MethodSignature) pg.getSignature();
		 Method method = methodSignature.getMethod();
		 
		 if (!method.isAnnotationPresent(IgnoreSecurity.class)||method.getAnnotation(IgnoreSecurity.class).val()) {
			return pg.proceed();
		  }
		 
		 HttpServletResponse response 
		   = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		 
		 HttpServletRequest request 
		   =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	     String token = request.getHeader(tokenName);
	     String userCode = request.getHeader(this.userCode);
	     
	     if ( token == null ) {    	
	    	new ResponseResult<>().responseFailMessage(response, "token为空");
			throw new TokenException("token为空");
		 }
	     if ( userCode == null ) {
	    	 new ResponseResult<>().responseFailMessage(response, "userCode为空");
	    	 throw new TokenException("userCode为空");
		 }
	     
	     //1.判断是否存在这个用户
	     Object object = tokenManager
	    		 .getValueFromMap(userCode,EntityConstants.REDIS_SESSION_NAME);
	     if (object==null) {
	    	 new ResponseResult<>().responseFailMessage(response, "不存在用户");
	    	 throw new TokenException();
		 }
	     //2.判断token是否有效
	     SessionInfo sessionInfo = (SessionInfo)object;	     
	     if (!sessionInfo.getToken().equals(token)) {
	    	 String message = String.format("token [%s] is invalid ", token);
	    	 throw new LoginException(message);
		 }
	     //3.判断是否拥有权限
	     //3.1判断是否需要权限校检
	     if (method.getAnnotation(IgnoreSecurity.class).authority()) {
	    	 String uri  = request.getRequestURI().replaceAll(request.getContextPath(), "");
	    	 //3.2判断请求的操作类型 读/写
			 Byte neetLevel = (byte)(uri.endsWith(".do") ? 2 : 1);
			 Map<String, Byte> authMap = authorityService.getKeyMap(userCode);
			 Byte level = null;
			 String[] tempArr = uri.split("/");
			//3.2  判断url类型 是否是 xx/s/number/xx.xx 这种形式的
			 String key = tempArr[tempArr.length-3].equals("s") ? tempArr[tempArr.length-4] : tempArr[tempArr.length-2];
			 System.out.println("key:"+key);
			 if (authMap == null || authMap.isEmpty() || (level = authMap.get(key)) == null 
					 || level < neetLevel ) {
				// new ResponseResult<>().responseFailMessage(response, "权限不足");
			     throw new AuthorityNotEnoughException();
			 }
		 }
     
	     // 调用目标方法
	        return pg.proceed();
	 }

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		if (StringUtil.isEmpty(tokenName)) {
	            tokenName = DEFAULT_TOKEN_NAME;
	    }
		this.tokenName = tokenName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserName(String userCode) {
		if (StringUtil.isEmpty(userCode)) {
			userCode = DEFAULT_USER_NAME;
        }
		this.userCode = userCode;
	}

	public RedisTokenManager getTokenManager() {
		return tokenManager;
	}

	public void setTokenManager(RedisTokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	
	 
	 

}
