package com.oo.businessplan.basic.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.oo.businessplan.additional.pojo.Msg;
import com.oo.businessplan.basic.service.MsgService;

@Service("msgService")
public class MsgServiceImpl implements MsgService{

	@Override
	public Msg generateMsg(String phoneNo, byte type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validMsg(String phoneNo, String verificationCode, byte type) {
		
		return false;
	}

}
