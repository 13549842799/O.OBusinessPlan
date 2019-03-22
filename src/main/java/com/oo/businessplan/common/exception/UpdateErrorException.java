package com.oo.businessplan.common.exception;

public class UpdateErrorException extends Exception {
	
	public UpdateErrorException(ErrorType type) {
		super(type.getMessage());
	}
	
	public enum ErrorType {
		
		OBJECT_NOT_ALL(1,"更新数量不正确"),
		
		OBJECT_FUNCTION_ERROR(2,"插入时发生未知异常");
		
		private int sign;
		private String message;
		
		private ErrorType(int sign,String message) {
			this.sign = sign;
			this.message = message;
		}

		public int getSign() {
			return sign;
		}

		public void setSign(int sign) {
			this.sign = sign;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		
	}

}
