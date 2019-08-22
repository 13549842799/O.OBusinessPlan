package com.oo.businessplan.upload.controller;

import java.util.List;
import java.util.Map;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.pageModel.MethodResult;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.redis.RedisManager;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.UpLoadUtil;
import com.oo.businessplan.upload.pojo.UploadFile;
import com.oo.businessplan.upload.service.UploadFileService;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-14 10:23:08
 */
@RestController
@RequestMapping(value = "/api/upload/uploadFile")
public class UploadFileController extends BaseController{

    @Autowired
    UploadFileService uploadFileService;
    
    @Autowired
	private UpLoadUtil upLoadUtil;
    
    private final String[] models = {"admin", "employee", "novel"};
    private final String[] types = {"img", "img", "img"};
    /**
     * 上传文件
     * @param request
     * @type 1-img 2-music
     * @return
     */
    @IgnoreSecurity
    @PostMapping(value = "/s/{key}/upload.do")
    public ResponseResult<UploadFile> uploadFile(HttpServletRequest request,
    		@PathVariable(name="key") String key,
    		@RequestParam(name="model")int model) {
    	
        ResponseResult<UploadFile> response = new ResponseResult<>();
     
        Map<String, MultipartFile> fileMap = upLoadUtil.getFile(key, request);
        MultipartFile file = null;
        if (fileMap == null || (file = fileMap.get(key)) == null) {
        	return response.fail("请提交文件");
        }
	    
        MethodResult<String> validResult = upLoadUtil.validFile(file, 120l, types[model]);
        
        if (validResult.fail()) {
        	return response.fail(validResult.getErrorMessage());
        }
        
        String path = upLoadUtil.filePersistence(file, File.separator + models[model], upLoadUtil.getRandomName(getAccountName(request)));
	   
        UploadFile obj = new UploadFile(path, (byte)model, file.getSize());
        uploadFileService.add(obj);
        //redis

        return response.success(obj);
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<UploadFile>> list(HttpServletRequest request) {
        ResponseResult<List<UploadFile>> response = new ResponseResult<>();

        return response.success();
    }
}