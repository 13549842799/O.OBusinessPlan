package com.oo.businessplan.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.system.mapper.AppVersionMapper;
import com.oo.businessplan.system.service.AppVersionService;
import com.oo.businessplan.system.pojo.AppVersion;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-06-22 20:57:31
 */
@Service("appVersionService")
public class AppVersionServiceImpl extends BaseServiceImpl<AppVersion> implements AppVersionService {

    @Autowired
    AppVersionMapper appVersionMapper;

	@Override
	public String getNewstWersionNo() {
		
		return appVersionMapper.getNewstWersionNo(DeleteFlag.VALID.getCode());
	}

	@Override
	public AppVersion getAppVersionInfo() {
		
		return appVersionMapper.getNewstVersion();
	}
    
    
}