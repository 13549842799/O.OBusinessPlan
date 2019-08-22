package com.oo.businessplan.upload.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
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
    
}