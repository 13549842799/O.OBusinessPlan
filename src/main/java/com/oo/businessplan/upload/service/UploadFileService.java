package com.oo.businessplan.upload.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.oo.businessplan.basic.service.BaseService;
import com.oo.businessplan.common.pageModel.MethodResult;
import com.oo.businessplan.upload.pojo.UploadFile;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-14 10:23:08
 */
public interface UploadFileService extends BaseService<UploadFile> {
	
	int deleteBatch(String ids, int creator);
}