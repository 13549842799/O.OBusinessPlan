package com.oo.businessplan.common.exception;

public class ObjectNotExistException extends CheckObjectExistException{
	
	
	     public  ObjectNotExistException(){
	    	   super("不存在此对象");
	     }
	     
	     public  ObjectNotExistException(String message){
	    	   super(message);
	     }

}
