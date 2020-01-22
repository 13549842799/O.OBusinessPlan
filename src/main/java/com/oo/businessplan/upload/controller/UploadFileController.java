package com.oo.businessplan.upload.controller;

import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
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
import com.oo.businessplan.common.enumeration.DeleteFlag;
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
    
    private final String[] models = {"admin", "employee", "novel", "section"};
    private final String[] types = {"img", "img", "img", "img"};
    /**
     * 上传文件
     * @param request
     * @type 1-img 2-music
     * @return
     */
    @IgnoreSecurity
    @PostMapping(value = "/upload.do")
    public ResponseResult<UploadFile> uploadFile(HttpServletRequest request,
    		UploadFile upLoadFile) {
    	
        ResponseResult<UploadFile> response = new ResponseResult<>();
     
        Map<String, MultipartFile> fileMap = upLoadUtil.getFile(upLoadFile.getName(), request);
        MultipartFile file = null;
        System.out.println(upLoadFile.getName());
        if (fileMap == null || (file = fileMap.get(upLoadFile.getName())) == null) {
        	return response.fail("请提交文件");
        }
        MethodResult<String> validResult = 
        		upLoadUtil.validFile(file, 120l, types[upLoadFile.getRelevance() - 1]);
        
        if (validResult.fail()) {
        	return response.fail(validResult.getErrorMessage());
        }
        String path = 
        		upLoadUtil.filePersistence(file, File.separator + models[upLoadFile.getRelevance() - 1], upLoadUtil.getRandomName(getAccountName(request)));

        upLoadFile.setPath(path);
        upLoadFile.setFileSize(file.getSize());
        upLoadFile.setTheType();
        upLoadFile.setCreator(currentAdminId(request));
        upLoadFile.setCreateTime(new Timestamp(new Date().getTime()));
        uploadFileService.add(upLoadFile, Long.class);
        //redis
        
        return response.success(upLoadFile);
    }
    
    //@IgnoreSecurity
    @GetMapping(value = "/s/{id}/file.re")
    public void readFile(HttpServletRequest request,
    		@PathVariable("id")Long id) {
        ResponseResult<List<UploadFile>> response = new ResponseResult<>();
        
        UploadFile uploadFile = new UploadFile();
        uploadFile.setId(id);
        uploadFile.setCreator(currentAdminId(request));
        uploadFile.setDelflag(DeleteFlag.VALID.getCode());
        
        uploadFile = uploadFileService.getById(uploadFile);
        if (uploadFile == null) {
        	
        }
        
        File file = new File(UpLoadUtil.LOCALPREFIX + uploadFile.getPath());
        
        if (!file.exists()) {
        	
        }
        try(FileInputStream in = new FileInputStream(file)) {
        	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<UploadFile>> list(HttpServletRequest request) {
        ResponseResult<List<UploadFile>> response = new ResponseResult<>();

        return response.success();
    }
    
    /**
     * 删除临时的图片文件(真删除)
     * @param request
     * @return
     */
    @IgnoreSecurity
    @DeleteMapping(value = "/s/{id}/del.do")
    public ResponseResult<String> delete(HttpServletRequest request,
    		@PathVariable("id")Long id) {
        ResponseResult<String> response = new ResponseResult<>();
        
        uploadFileService.deleteTempFile(id, currentAdminId(request));

        return response.success();
    }
}