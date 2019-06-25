package com.oo.businessplan.basic.service;

import java.util.Map;

import com.oo.businessplan.additional.pojo.Msg;

public interface MsgService {
	
	   Msg generateMsg(String phoneNo,byte type);
	   
	   /**
	    * 检验验证码是否正确
	    * @param phoneNo 手机号
	    * @param verificationCode 验证码
	    * @param type 验证的类型 
	    * @return  1-正确  0-错误
	    */
	   boolean validMsg(String phoneNo,String verificationCode,byte type);
	   
}
