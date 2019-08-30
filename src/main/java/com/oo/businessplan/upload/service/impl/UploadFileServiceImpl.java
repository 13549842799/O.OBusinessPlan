package com.oo.businessplan.upload.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.common.util.UpLoadUtil;
import com.oo.businessplan.upload.mapper.UploadFileMapper;
import com.oo.businessplan.upload.pojo.UploadFile;
import com.oo.businessplan.upload.service.UploadFileService;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-14 10:23:08
 */
@Service("uploadFileService")
public class UploadFileServiceImpl extends BaseServiceImpl<UploadFile> implements UploadFileService {

    @Autowired
    UploadFileMapper uploadFileMapper;
    
    @Autowired
    UpLoadUtil util;

	@Override
	public boolean delete(UploadFile t) {
		if (t== null || t.getId() == null) {
			return false;
		}
		t = uploadFileMapper.getById(t);
		if (!super.delete(t)) {
			return false;
		}	
		return util.deleteFile(t.getPath());
	}

	@Override
	public int deleteBatch(String ids, int creator) {
		if (StringUtil.isEmpty(ids)) {
			return 0;
		}
		int count = uploadFileMapper.deleteBatch(ids, creator);
		String[] paths = uploadFileMapper.getFilesPath(ids, creator);
		for (int i = 0; i< paths.length; i++) {
			util.deleteFile(paths[i]);
		}
		return count;
	}

	@Override
	public void relatedUserAndFile(int adminId, String ids) {
		int count = uploadFileMapper.updateObjId(adminId, ids);
	}
    
    
    
}