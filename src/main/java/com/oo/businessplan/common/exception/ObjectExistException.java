package com.oo.businessplan.common.exception;

public class ObjectExistException extends CheckObjectExistException{
	
	
	     public  ObjectExistException(){
	    	   super("此对象已存在");
	     }
	     
	     public  ObjectExistException(String message){
	    	   super(message);
	     }

}
