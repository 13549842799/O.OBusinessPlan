package com.oo.businessplan.basic.service;

import java.util.Map;

import com.oo.businessplan.additional.pojo.Msg;

public interface MsgService {
	
	   Msg generateMsg(String phoneNo,byte type);
	   
	   Map<String,String> validMsg(String phoneNo,String verificationCode,byte type);
	   

}
