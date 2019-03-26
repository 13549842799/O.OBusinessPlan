package com.oo.businessplan.common.exception.login;

/**
 * 登录异常，当检测到当前登录的名单中不存在目标用户，则抛出此异常
 * @author cyz
 *
 */
public class LoginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2974335228502392695L;

	public LoginException() {
		super("登录异常，请重新登录");
	}

	public LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public LoginException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LoginException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LoginException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
