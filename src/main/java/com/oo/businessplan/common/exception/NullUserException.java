package com.oo.businessplan.common.exception;

public class NullUserException extends RuntimeException{

	public NullUserException() {
		super("此账号不存在");
	}

	
}
