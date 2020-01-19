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
	
	/**
	 * 删除临时文件,这个文件必须没有与其它实体关联
	 * @param id
	 * @param creator
	 * @return
	 */
	int deleteTempFile(long id, int creator);
	
	/**
	 * 将实体和对应的id的文件记录进行物理关联
	 * @param objId
	 * @param ids
	 */
	void relatedUserAndFile(long objId, String ids);
	
	/**
	 * 关联实体和文件, 如果以前存在关联的文件，则删除旧的关联文件
	 * @param adminId
	 * @param relevance
	 * @param objId
	 * @param id
	 */
	void relatedObjdAndFile(int adminId, byte relevance, long objId, long id);
}