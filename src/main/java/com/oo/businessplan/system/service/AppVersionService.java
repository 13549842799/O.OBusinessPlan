package com.oo.businessplan.system.service;

import com.oo.businessplan.basic.service.BaseService;
import com.oo.businessplan.system.pojo.AppVersion;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-06-22 20:57:31
 */
public interface AppVersionService extends BaseService<AppVersion> {
	
	/**
	 * 获取最新版本的版本号
	 * @return
	 */
	String getNewstWersionNo();
	
	/**
	 * 获取最新版本号的相关信息
	 * @return
	 */
	AppVersion getAppVersionInfo();
}