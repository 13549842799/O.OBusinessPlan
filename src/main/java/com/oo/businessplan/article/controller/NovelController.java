package com.oo.businessplan.article.controller;

import java.util.List;
import java.util.Map;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.pageModel.MethodResult;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.common.util.UpLoadUtil;
import com.oo.businessplan.article.service.NovelService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.pojo.entity.Novel;
import com.oo.businessplan.article.pojo.form.NovelForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-03-28 15:01:16
 */
@RestController
@RequestMapping(value = "/api/article/novel")
public class NovelController extends BaseController{

    @Autowired
    NovelService novelService;
    
    @Autowired
	private UpLoadUtil upLoadUtil;
    
    @IgnoreSecurity
    @PostMapping(value = "/save.do")
    public ResponseResult<Novel> createNovel(HttpServletRequest request,
    		Novel novel, String fileName) {
        ResponseResult<Novel> response = new ResponseResult<>();
        System.out.println("进入save接口");
        if (StringUtil.isEmpty(novel.getTitle())) {
        	return response.fail("请输入标题");
        }
        
        if (StringUtil.isNotEmpty(novel.getContent()) && novel.getContent().length() > 600) {
        	return response.fail("简介过长");
        }
        MethodResult<String> saveCover = null;
        if (StringUtil.isNotEmpty(fileName) && (saveCover = saveCover(request, fileName)).fail()) {
        	return response.fail(saveCover.getErrorMessage());
        }
        novel.setCover(saveCover == null ? null : saveCover.getData());
        Integer creator = currentAdminId(request);
        if (novel.getId() == null) {
        	novel.setCreator(creator);      
        	novel.setCreateTime(new Timestamp(new Date().getTime()));
        	novelService.add(novel, Integer.class);
        } else {
        	novel.setModifier(creator);
        	if (novelService.update(novel) != 1) {
        		return response.fail("更新失败");
        	}
        	Novel oldNovel = novelService.getById(novel);
        	if (oldNovel != null && StringUtil.isNotEmpty(oldNovel.getCover())) {
        		upLoadUtil.deleteFile(UpLoadUtil.LOCALPREFIX + saveCover.getData());
        	}
        }
    
        return response.success(novel);
    }
    
    
    @IgnoreSecurity
    @PostMapping(value = "/s/{id}/cover.do")
    public ResponseResult<String> updateCover(HttpServletRequest request,
    		@PathVariable(name="id")int id,
    		@RequestParam(name = "key", required = true) String key) {
        ResponseResult<String> response = new ResponseResult<>();
        
        MethodResult<String> saveCover = saveCover(request, key);
        
        if (saveCover.fail()) {
        	return response.fail(saveCover.getErrorMessage());
        }
        
        Novel old = novelService.getById(new Novel(id, currentAdminId(request))), novel = new Novel();
        
        novel.setId(id);
        novel.setCover(saveCover.getData());
        if(novelService.update(novel) == 1 && old != null) {
        	upLoadUtil.deleteFile(UpLoadUtil.LOCALPREFIX + saveCover.getData());
        	return response.success(saveCover.getData());
        };
        return response.fail("发生未知错误");
    }


	private MethodResult<String> saveCover(HttpServletRequest request, String key) {
		
		MethodResult<String> result = new MethodResult<>();
		
		Map<String, MultipartFile> fileMap = upLoadUtil.getFile(key, request);
        MultipartFile file = null;
        if (fileMap == null || (file = fileMap.get(key)) == null) {
        	return result.fail("请提交文件");
        }
	    
        MethodResult<String> validResult = upLoadUtil.validFile(file, 120l, upLoadUtil.types[2]);
        
        if (validResult.fail()) {
        	return result.fail(validResult.getErrorMessage());
        }
        
        String path = upLoadUtil.filePersistence(file, File.separator + upLoadUtil.models[2], upLoadUtil.getRandomName(getAccountName(request)));
		System.out.println("path:" + path);
        return result.success(path);
	}
    
    
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<PageInfo<NovelForm>> list(HttpServletRequest request,
    		NovelForm form) {
        ResponseResult<PageInfo<NovelForm>> response = new ResponseResult<>();
        form.setDelflag(DeleteFlag.VALID.getCode());
        form.setCreator(currentAdminId(request));
        PageInfo<NovelForm> page = novelService.getPage(form);    
        return response.success(page);
    }
    
    @DeleteMapping("/s/{id}/delete.do")
    @IgnoreSecurity
    public ResponseResult<String> deleteNovel(HttpServletRequest request,
    		@PathVariable(name="id") Integer id) {
    	ResponseResult<String> response = new ResponseResult<>();
    	
    	Integer adminId = currentAdminId(request);
    	Novel novel = new Novel(id, adminId);
    	novel.setModifier(adminId);
    	if (novelService.delete(novel)) {
    		return response.success();
    	}
    	return response.fail("删除异常");
    }
    
    
    
    
    
}