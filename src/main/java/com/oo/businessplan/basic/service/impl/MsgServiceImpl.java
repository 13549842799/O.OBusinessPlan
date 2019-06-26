package com.oo.businessplan.basic.service.impl;

import org.springframework.stereotype.Service;

import com.oo.businessplan.additional.pojo.Msg;
import com.oo.businessplan.basic.service.MsgService;
import com.oo.businessplan.basic.service.support.RedisCacheSupport;

@Service("msgService")
public class MsgServiceImpl extends RedisCacheSupport<Msg> implements MsgService{

	@Override
	public Msg generateMsg(String phoneNo, byte type) {
		
		return null;
	}

	@Override
	public boolean validMsg(String phoneNo, String verificationCode, byte type) {
		
		return false;
	}

	@Override
	public String generateMsg(String phone) {
		
		return null;
	}

}
