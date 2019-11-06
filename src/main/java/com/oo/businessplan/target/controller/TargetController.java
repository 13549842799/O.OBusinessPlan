package com.oo.businessplan.target.controller;

import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.basic.service.PageService;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.pageModel.PageParams;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.valid.ValidService;
import com.oo.businessplan.target.service.TargetService;
import com.oo.businessplan.target.pojo.entity.Target;


/**
 * 保存目标： 
 * 废弃目标：
 * 
 * 
 * 
 * @author cyz
 * @version 创建时间：2019-10-11 08:57:13
 */
@RestController
@RequestMapping(value = "/api/target/target")
public class TargetController extends BaseController{

    @Autowired
    TargetService targetService;
    
    @Resource(name="targetService")
    PageService<Target> pageService;
    
    @Autowired
    ValidService validUtil;
    
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<PageInfo<Target>> list(HttpServletRequest request,
    		@RequestParam(value="pageNum", required=false)Integer pageNum,
			@RequestParam(value="pageSize", required=false)Integer pageSize,
    		Target target) {
        ResponseResult<PageInfo<Target>> response = new ResponseResult<>();
        if (target == null) {
        	target = new Target();
        }
        System.out.println(target.getType());
        target.setCreator(currentAdminId(request));
        target.setDelflag(DeleteFlag.VALID.getCode());
        PageParams<Target> params = new PageParams<>(target, pageNum, pageSize);
        
        PageInfo<Target> page = pageService.getPage(params);
        
        return response.success(page);
    }
    
    @IgnoreSecurity
    @PostMapping(value = "/save.do")
    public ResponseResult<Target> add(HttpServletRequest request,
    		@RequestBody(required=true)Target target) {
        ResponseResult<Target> response = new ResponseResult<>();
        
        if (target.getState() != Target.DRAFT) {
        	String result = validUtil.validReturnFirstError(target);
            if (result != null) {
            	return response.fail(result);
            }
        }
        if (target.getId() == null) {
        	target.setCreator(currentAdminId(request));
        	target.setCreateTime(new Timestamp(new Date().getTime()));
        	targetService.add(target, Integer.class);
        } else {
        	targetService.update(target);
        }        
        return response.success(target);
    }
    
    /**
     * 废弃目标
     * @param request
     * @param id
     * @return
     */
    @IgnoreSecurity
    @PostMapping(value = "/s/{id}/delete.do")
    public ResponseResult<Target> delete(HttpServletRequest request,
    		@PathVariable("id") Integer id) {
        ResponseResult<Target> response = new ResponseResult<>();
        
        Target target = new Target();
        target.setId(id);
        target = targetService.getById(target);
        
        Integer adminId = currentAdminId(request);
        if (adminId != target.getCreator()) {
        	return response.fail("没有权限");
        }
    
        return response.deleteResult(targetService.delete(target));
    }
    
}