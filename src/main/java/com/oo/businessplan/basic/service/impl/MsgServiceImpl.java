package com.oo.businessplan.basic.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oo.businessplan.additional.pojo.Msg;
import com.oo.businessplan.basic.service.MsgService;
import com.oo.businessplan.common.redis.RedisMsgManager;
import com.oo.businessplan.common.util.StringUtil;

@Service("msgService")
public class MsgServiceImpl implements MsgService{
	
	@Autowired
	private RedisMsgManager manager;

	@Override
	public Msg generateMsg(String phoneNo, byte type) {
		
		return null;
	}

	@Override
	public boolean validMsg(String phoneNo, String verificationCode, byte type) {
		
		String code = manager.msgCode(phoneNo, type);
		if (!StringUtil.equalsNotNull(code, verificationCode)) {
			return false;
		}
		manager.deleteCode(phoneNo, type);
		return true;
	}

	@Override
	public boolean sendMeg(Msg msg) {
		manager.saveMsg(msg);
		return true;
	}

}
