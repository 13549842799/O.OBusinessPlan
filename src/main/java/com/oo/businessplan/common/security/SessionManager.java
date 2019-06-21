package com.oo.businessplan.common.security;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.oo.businessplan.common.net.SessionInfo;

public interface SessionManager {
	
	
	default String createToken() {
		return UUID.randomUUID().toString();
	};
	
	void cancelToken(String token);
	
	void saveSeesion(SessionInfo info, String key, long expired, TimeUnit timeUnit);
	
	SessionInfo getSessionInfo(String key);
	
	boolean checkSeesionExists(String key);
	
	void removeSession(String key);

}
