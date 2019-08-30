package com.oo.businessplan.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.admin.mapper.AccountManagerMapper;
import com.oo.businessplan.admin.service.AccountManagerService;
import com.oo.businessplan.admin.pojo.entity.AccountManager;
import com.oo.businessplan.admin.pojo.entity.Key;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-07 17:28:11
 */
@Service("accountManagerService")
public class AccountManagerServiceImpl extends BaseServiceImpl<AccountManager> implements AccountManagerService {

    @Autowired
    AccountManagerMapper accountManagerMapper;

	@Override
	public void saveKey(Key key) {
		 accountManagerMapper.insertKey(key);
	}

	@Override
	public String getKey(int adminId) {
		return accountManagerMapper.getKey(adminId);
	}
    
    
}