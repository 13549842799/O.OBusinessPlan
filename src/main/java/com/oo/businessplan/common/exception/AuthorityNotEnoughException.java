package com.oo.businessplan.common.exception;

public class AuthorityNotEnoughException extends RuntimeException{
	
	   /**
	 * 
	 */
	private static final long serialVersionUID = 2589946309764804448L;

	public AuthorityNotEnoughException() {
		   super("权限不足");
	   }

}
