package com.oo.businessplan.upload.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.enumeration.DeleteFlag;
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
		return util.deleteFileWillRoot(t.getPath());
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
	public void relatedUserAndFile(long objId, String ids) {
		int count = uploadFileMapper.updateObjId(objId, ids);
	}

	@Override
	public int deleteTempFile(long id, int creator) {
		UploadFile upload = new UploadFile();
		upload.setId(id);
		upload.setCreator(creator);
		upload.setDelflag(DeleteFlag.VALID.getCode());
		
		upload = uploadFileMapper.getById(upload);
		if (upload == null) {
			return 0;
		}
		int count = uploadFileMapper.deleteReal(id, creator);
		
		util.deleteFile(upload.getPath());
		
		return count;
	}

	@Override
	public void relatedObjdAndFile(int adminId, byte relevance, long objId, long id) {
		UploadFile file = new UploadFile();
		file.setId(id);
		file.setCreator(adminId);
		file.setRelevance(relevance);
		file.setObjId(objId);
		if (uploadFileMapper.checkFileExist(file) == null) {
			return;
		}
		
		List<UploadFile> oldIds = uploadFileMapper.checkFileExistInObj(file);
		if (oldIds != null && oldIds.size() > 0) {
			final StringBuilder sb = new StringBuilder();
			oldIds.forEach(o -> sb.append(String.valueOf(o.getId())).append(","));
			String ids = sb.deleteCharAt(sb.length() - 1).toString();
			uploadFileMapper.deleteBatch(ids, adminId);
			for (UploadFile uploadFile : oldIds) {
				util.deleteFileWillRoot(uploadFile.getPath());
			}
		}
		uploadFileMapper.updateObjId(objId, String.valueOf(objId));
	}
    
    
    
}