package com.oo.businessplan.article.controller;

import java.util.List;
import java.util.Map;
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
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.article.service.SectionService;
import com.oo.businessplan.article.pojo.entity.Section;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-20 15:55:56
 */
@RestController
@RequestMapping(value = "/api/article/section")
public class SectionController extends BaseController{

    @Autowired
    SectionService sectionService;
    
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<Section>> list(HttpServletRequest request) {
        ResponseResult<List<Section>> response = new ResponseResult<>();

        return response.success();
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/addOrUpdate.do")
    public ResponseResult<Section> section(HttpServletRequest request,
    		@RequestBody(required = true) Section section) {
        ResponseResult<Section> response = new ResponseResult<>();
        
        section.setWordsNum(StringUtil.isNotEmpty(section.getContent()) ? section.getContent().length() : 0);
        
        sectionService.add(section, Section.class);
        
        return response.success();
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/s/{id}/read.re")
    public ResponseResult<Section> getSection(HttpServletRequest request,
    		@PathVariable(name="id")Long id) {
        ResponseResult<Section> response = new ResponseResult<>();
        
        Section section = new Section();
        section.setId(id);
        section.setCreator(currentAdminId(request));
        section = sectionService.getExpandSection(section);
        
        return response.success(section);
    }
    
    @IgnoreSecurity
    @DeleteMapping(value = "/s/{id}/delete.do")
    public ResponseResult<Section> delSection(HttpServletRequest request,
    		@PathVariable(name="id")Long id) {
        ResponseResult<Section> response = new ResponseResult<>();     
        
        Integer user = currentAdminId(request);
        
        Section section = new Section();
        section.setId(id);
        section.setCreator(user);
        section.setModifier(user);
        System.out.println("sectionId=" + section.getId());
        return response.deleteResult(sectionService.delete(section));
    }
}