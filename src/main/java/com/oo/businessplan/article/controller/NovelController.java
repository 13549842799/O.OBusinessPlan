
package com.oo.businessplan.article.controller;

import java.util.Map;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.constant.ArticleConstant.ArticleType;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.pageModel.MethodResult;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.common.util.UpLoadUtil;
import com.oo.businessplan.upload.builder.UploadBuilder;
import com.oo.businessplan.upload.pojo.UploadFile;
import com.oo.businessplan.upload.service.UploadFileService;
import com.oo.businessplan.article.service.LabelService;
import com.oo.businessplan.article.service.NovelService;
import com.oo.businessplan.article.service.PortionService;
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
    private LabelService labelService;
    
    @Autowired
    PortionService portionService;
    
    @Autowired
	private UpLoadUtil upLoadUtil;
    
    @Autowired
    private UploadFileService uploadFileService;
    
    @IgnoreSecurity
    @PostMapping(value = "/save.do")
    public ResponseResult<NovelForm> createNovel(HttpServletRequest request,
    		NovelForm novel, String fileName) {
        ResponseResult<NovelForm> response = new ResponseResult<>();
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
        Integer creator = currentAdminId(request);
        Timestamp now = new Timestamp(new Date().getTime());
        if (novel.getId() == null) {
        	novel.setCreator(creator);      
        	novel.setCreateTime(now);
        	novelService.add(novel, Integer.class);
        	//添加小说相关分管
        	portionService.addDefaultPosition(novel.getId(), creator);
        } else {
        	novel.setModifier(creator);
        	if (novelService.update(novel) != 1) {
        		return response.fail("更新失败");
        	}
        	UploadFile old = new UploadFile((byte)3, novel.getId().longValue());
        	List<UploadFile> ufs = uploadFileService.getList(old);
        	if (ufs != null && ufs.size() > 0) {
        		uploadFileService.delete(ufs.get(0));    		
        	}
        }
      //保存封面文件
        if (saveCover != null) {
            UploadFile newCover = UploadBuilder.newBuilder().Name(fileName).Path(saveCover.getData())
            		.Relevance((byte)3).ObjId(novel.getId().longValue()).creator(creator).createTime(now).delflag(DeleteFlag.VALID.getCode()).build();
            uploadFileService.add(newCover);
        }
        //批量保存标签
        labelService.batchAddOrUpdate(novel.getLabelList(), novel, ArticleType.NOVEL);      
        NovelForm novelForm = novelService.getComplete(novel);
        
        return response.success(novelForm);
    }
    
    /**
     * 新的保存接口,独立出封面文件的保存逻辑，这里只保存小说实体和封面文件路径
     * @param request
     * @param novel
     * @param fileName
     * @return
     */
    @IgnoreSecurity
    @PostMapping(value = "/save2.do")
    public ResponseResult<NovelForm> createNovel2(HttpServletRequest request,
    		@RequestBody NovelForm novel) {
        ResponseResult<NovelForm> response = new ResponseResult<>();
        if (StringUtil.isEmpty(novel.getTitle())) {
        	return response.fail("请输入标题");
        }
        
        if (StringUtil.isNotEmpty(novel.getContent()) && novel.getContent().length() > 600) {
        	return response.fail("简介过长");
        }

        Integer creator = currentAdminId(request);
        if (novel.getId() == null) {
        	novel.setCreator(creator);      
        	novel.setCreateTime(new Timestamp(new Date().getTime()));
        	novel.setNovelState(Novel.UNSTART);
        	novelService.add(novel, Integer.class);
        	//添加小说相关分管
        	portionService.addDefaultPosition(novel.getId(), creator);
        } else {
        	novel.setModifier(creator);
        	if (novelService.update(novel) != 1) {
        		return response.fail("更新失败");
        	}
        }
        //关联封面与小说
        if (StringUtil.isNotEmpty(novel.getCover())) {
        	//如果存在旧的封面，则删除旧的封面(假删除)
            uploadFileService.relatedObjdAndFile(creator, UploadFile.NOVEL, novel.getId(), Long.parseLong(novel.getCover()));	
        }
        
        //批量保存标签
        labelService.batchAddOrUpdate(novel.getLabelList(), novel, ArticleType.NOVEL);      
        NovelForm novelForm = novelService.getComplete(novel);
        
        return response.success(novelForm);
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
        	return result.fail("no_file");
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
        System.out.println(currentAdminId(request));
        PageInfo<NovelForm> page = novelService.getPage(form);    
        return response.success(page);
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/s/{id}/novel.re")
    public ResponseResult<NovelForm> novel(
    		HttpServletRequest request,
    		@PathVariable(name="id")Integer id) {
        ResponseResult<NovelForm> response = new ResponseResult<>();
        Novel param = new Novel();
        param.setCreator(currentAdminId(request));
        param.setId(id);
        NovelForm novel = novelService.getComplete(param);
        
        return response.success(novel);
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