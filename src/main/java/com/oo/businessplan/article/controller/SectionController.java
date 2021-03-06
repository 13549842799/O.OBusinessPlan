package com.oo.businessplan.article.controller;

import java.util.List;
import java.util.Map;
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
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.annotation.UseTrigger;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.upload.service.UploadFileService;
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
    
    @Autowired
    UploadFileService uploadFileService;
    
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<Section>> list(HttpServletRequest request,
    		Section section) {
        ResponseResult<List<Section>> response = new ResponseResult<>();

        return response.success(sectionService.getSimpleSections(section));
    }
    
    /**
     * 
     * @param request
     * @param section
     * @return
     */
    @IgnoreSecurity
    @PostMapping(value = "/addOrUpdate.do")
    public ResponseResult<Section> section(HttpServletRequest request,
    		@RequestBody(required = true) Section section) {
        ResponseResult<Section> response = new ResponseResult<>();
        int user = currentAdminId(request);
        section.setWordsNum(StringUtil.returenOnlyWords(section.getContent()).length());
        /*if (section.getWordsNum() < 500) {
        	return response.fail("内容不能少于500字");
        }*/
        section.setCreator(user);
        if (section.getId() == null) {       	
        
        	section.setCreateTime(new Timestamp(new Date().getTime()));
			sectionService.add(section, Long.class);
			
			section = sectionService.getById(section);
			Long lastId = sectionService.lastSectionId(section);
			if (lastId != null) {
				Section last = new Section();
				last.setId(lastId);
				last.setNextSection(section.getId());
				sectionService.update(last);
			}
			Long nextId = sectionService.nextSectionId(section);
			if (nextId != null) {
				Section next = new Section();
				next.setId(nextId);
				next.setLastSection(section.getId());
				sectionService.update(next);
			}
			section = new Section(section.getId());
			section.setLastSection(lastId);
			section.setNextSection(nextId);
		}
        
        section.setModifier(user);
		sectionService.update(section);
		
        if (StringUtil.isNotEmpty(section.getDelImagesId())) {
        	uploadFileService.deleteBatch(section.getDelImagesId(), user);
        }
        if (StringUtil.isNotEmpty(section.getAddImagesId())) {
        	uploadFileService.relatedUserAndFile(section.getId(), section.getAddImagesId());
        }     
        return response.success(section);
    }
    
    /**
     * 电脑客户端调用的章节保存接口
     * @param request
     * @param section
     * @return
     */
    @UseTrigger(name="alterForInsertSection, alterForUpdateSection", effect="修改小说和分卷的字数合计，章节合计，状态和最新章标记")
    @IgnoreSecurity
    @PostMapping(value = "/addOrUpdate2.do")
    public ResponseResult<Section> section2(HttpServletRequest request,
    		@RequestBody(required = true) Section section) {
        ResponseResult<Section> response = new ResponseResult<>();
        int user = currentAdminId(request);
        section.setWordsNum(StringUtil.isNotEmpty(section.getContent()) ? section.getContent().length() : 0);
        section.setCreator(user);
        Timestamp now = new Timestamp(new Date().getTime());
        if (section.getId() == null) {       	   
        	section.setCreateTime(now);
			sectionService.add(section, Long.class);
			
			section = sectionService.getById(section);
			Long lastId = sectionService.lastSectionId(section);
			if (lastId != null) {
				Section last = new Section();
				last.setId(lastId);
				last.setNextSection(section.getId());
				sectionService.update(last);
			}
			Long nextId = sectionService.nextSectionId(section);
			if (nextId != null) {
				Section next = new Section();
				next.setId(nextId);
				next.setLastSection(section.getId());
				sectionService.update(next);
			}
			section = new Section(section.getId());
			section.setLastSection(lastId);
			section.setNextSection(nextId);
		}
        
        section.setModifier(user);
        section.setModifierTime(now);
		sectionService.update(section);
		
        if (StringUtil.isNotEmpty(section.getDelImagesId())) {
        	uploadFileService.deleteBatch(section.getDelImagesId(), user);
        }
        section.setCreator(user);
        return response.success(sectionService.getById(section));
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/s/{id}/read.re")
    public ResponseResult<Section> getSection(HttpServletRequest request,
    		@PathVariable(name="id")Long id,
    		@RequestParam(name="novelId", required=false)Integer novelId) {
        ResponseResult<Section> response = new ResponseResult<>();
        
        Section section = new Section();
        section.setId(id);
        section.setNovelnId(novelId);
        section.setCreator(currentAdminId(request));
        section = sectionService.getExpandSection(section);
        
        return response.success(section);
    }
    
    @UseTrigger(name="alterForDeleteSection")
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
        return response.deleteResult(sectionService.delete(section));
    }
}