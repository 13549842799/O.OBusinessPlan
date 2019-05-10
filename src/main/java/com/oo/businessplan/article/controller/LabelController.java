package com.oo.businessplan.article.controller;

import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.article.service.LabelService;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.pojo.entity.Label;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-04-19 15:55:26
 */
@RestController
@RequestMapping(value = "/api/article/label")
public class LabelController extends BaseController{

    @Autowired
    LabelService labelService;
    
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<Label>> list(
    		HttpServletRequest request) {
        ResponseResult<List<Label>> response = new ResponseResult<>();
        Label label = new Label(null, DeleteFlag.VALID.getCode());
        List<Label> labels = labelService.getList(label);
        return response.success(labels);
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/page.re")
    public ResponseResult<PageInfo<Label>> page(
    		@RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
    		@RequestParam(value="pageSize", defaultValue="8") Integer pageSize,
    		@RequestParam(value="name", required=false) String name,
    		HttpServletRequest request) {
        ResponseResult<PageInfo<Label>> response = new ResponseResult<>();
        Label label = new Label(null, DeleteFlag.VALID.getCode());
        label.setName(name);
        label.setAdminId(currentAdminId(request));
        PageInfo<Label> page = labelService.page(label, pageNum, pageSize);
        return response.success(page);
    }
    
    @IgnoreSecurity
    @PostMapping(value = "/add.do")
    public ResponseResult<Label> add(HttpServletRequest request,
    		@RequestBody Label label
    		) {
        ResponseResult<Label> response = new ResponseResult<>();
        Integer adminId = currentAdminId(request);
        label.setAdminId(adminId);
        label.setCreator(adminId);
        label.setCreateTime(new Timestamp(new Date().getTime()));
        label.setDelflag(DeleteFlag.VALID.getCode());     
        try {
			labelService.add(label, Integer.class); 
			return response.success(label);
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			return response.fail("已经存在相同的名字");
		}       
    }
    
    @IgnoreSecurity
    @PatchMapping(value = "/update.do")
    public ResponseResult<Object> updateLabel(
    		@RequestBody Label label,
    		HttpServletRequest request) {
        ResponseResult<Object> response = new ResponseResult<>();
        label.setAdminId(currentAdminId(request));
        
		try {
			int count = labelService.update(label);
			 return response.updateResult(count);
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			return response.fail("已经存在相同的名字");
		}
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/useCount.re")
    public ResponseResult<Integer> hasUseLabel(
    		@RequestParam(value="id") int id,
    		HttpServletRequest request) {
        ResponseResult<Integer> response = new ResponseResult<>();
        
        int count = labelService.hasUseCount(id);
        
        return response.success(count);
        
    }
    
    @IgnoreSecurity
    @DeleteMapping(value = "/s/{id}/delete.do")
    public ResponseResult<Object> removeLabel(
    		@PathVariable int id,
    		HttpServletRequest request) {
        ResponseResult<Object> response = new ResponseResult<>();
       
        labelService.delete(new Label(id));
        
        return response.success();
        
    }
    
}