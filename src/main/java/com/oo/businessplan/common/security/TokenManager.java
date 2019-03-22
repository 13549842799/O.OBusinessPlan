package com.oo.businessplan.common.security;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.net.SessionInfo;

public interface TokenManager {
	
	
	String createToken(SessionInfo sessionInfo);
	
	boolean checkToken(String token,String code) throws ObjectNotExistException;
	
	void cancelToken(String token);
	

}
