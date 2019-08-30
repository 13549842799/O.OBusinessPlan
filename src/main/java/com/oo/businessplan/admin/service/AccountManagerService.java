package com.oo.businessplan.admin.service;

import com.oo.businessplan.basic.service.BaseService;
import com.oo.businessplan.common.util.DesUtil;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.admin.pojo.entity.AccountManager;
import com.oo.businessplan.admin.pojo.entity.Key;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-07 17:28:11
 */
public interface AccountManagerService extends BaseService<AccountManager> {
	
	
	public default String encryptPassword(String password, int adminId) {
		String key = getKey(adminId);
		if (StringUtil.isEmpty(password) || StringUtil.isEmpty(key)) {
			return null;
		}
		return DesUtil.getInstance().encrypt(password.getBytes(), key.getBytes());
	}
	
	public default String decryptPassword(String encryptedPassword, int adminId) {
		String key = getKey(adminId);
		if (StringUtil.isEmpty(encryptedPassword) || StringUtil.isEmpty(key)) {
			return null;
		}
		return DesUtil.getInstance().decrypt(encryptedPassword.getBytes(), key.getBytes());
	}

	public void saveKey(Key key);
	
	String getKey(int adminId);

	default boolean haskey(Integer currentAdminId) {
		return StringUtil.isNotEmpty(getKey(currentAdminId));
	};
}