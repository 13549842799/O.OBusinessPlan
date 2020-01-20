package com.oo.businessplan.article.controller;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.article.service.PortionService;
import com.oo.businessplan.article.pojo.entity.Portion;
import com.oo.businessplan.article.pojo.entity.Section;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-20 10:21:41
 */
@RestController
@RequestMapping(value = "/api/article/portion")
public class PortionController extends BaseController{

    @Autowired
    PortionService portionService;
    
    @IgnoreSecurity
    @GetMapping(value = "/s/{novel}/list.re")
    public ResponseResult<List<Portion>> list(HttpServletRequest request,
    		@PathVariable(name="novel") Integer novelId,
    		@RequestParam(name="portion", required = false)Integer id,
    		@RequestParam(name="expand", required = false, defaultValue = "0")int  expand) {
    	
        ResponseResult<List<Portion>> response = new ResponseResult<>();
        Portion param = new Portion();
        param.setId(id);
        param.setCreator(currentAdminId(request));
        param.setNovelId(novelId);
        param.setDelflag(DeleteFlag.VALID.getCode());
        List<Portion> novelPortions = expand == 1 ? portionService.getExpandList(param) : portionService.getList(param);
        
        return response.success(novelPortions);
    }
    
    @IgnoreSecurity
    @PostMapping(value = "/add.do")
    public ResponseResult<Portion> addPortion(HttpServletRequest request,
    		@RequestBody Portion portion) {
    	
        ResponseResult<Portion> response = new ResponseResult<>();
        if (portion.getNovelId() == null) {
        	return response.fail("请选择小说");
        }
        if (portion.judgeWorksInfo()) {
        	return response.fail("参数错误");
        }    
        portion.setDelflag(DeleteFlag.VALID.getCode());
        if (portion.getId() == null) {
        	portion.setWordsNum(0);
        	portion.setSectionNum(0);
        	portion.setCreator(currentAdminId(request));
        	portion.setCreateTime(new Timestamp(new Date().getTime()));
        	portionService.add(portion, Integer.class);
        	return response.success(portionService.getById(portion));
        } else {
        	portion.setModifier(currentAdminId(request));
        	return response.updateResult(portionService.update(portion));
        }
    }
    
    @IgnoreSecurity
    @PostMapping(value = "/update.do")
    public ResponseResult<Portion> updatePortion(HttpServletRequest request,
    		@RequestBody Portion portion) {
    	
        ResponseResult<Portion> response = new ResponseResult<>();
        if (portion.getNovelId() == null) {
        	return response.fail("请选择小说");
        }
        portion.setCreator(currentAdminId(request));       
        portion.setDelflag(DeleteFlag.VALID.getCode());
        
        portionService.add(portion, Integer.class);
        
        return response.success(portion);
    }
    
    @IgnoreSecurity
    @DeleteMapping(value = "/s/{id}/delete.do")
    public ResponseResult<Portion> deletePortion(HttpServletRequest request,
    		@PathVariable(name="id")Integer id) {
    	
        ResponseResult<Portion> response = new ResponseResult<>();
        
        if (id == null) {
        	return response.fail("请选择小说");
        }       
        Portion portion = portionService.getById(new Portion(id));
        if (portion == null) {
        	return response.fail("不存在小说");
        }
        if (portion.judgeWorksInfo()) {
        	return response.fail("参数错误");
        }
        Integer user = currentAdminId(request);
        portion.setCreator(user);       
        portion.setDelflag(DeleteFlag.VALID.getCode());
        portion.setModifier(user);
        
        return response.deleteResult(portionService.delete(portion));
    }
}