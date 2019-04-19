package com.oo.businessplan.article.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.pojo.entity.Diary;
import com.oo.businessplan.article.pojo.form.DiaryForm;
import com.oo.businessplan.article.service.DiaryService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.AddErrorException;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;

/**
 * 这是一个关于日记相关的controller，用于创建日记，保存日记，读取日记等
 * @author cyz
 *
 */
@RequestMapping("/api/article/diary")
@RestController
public class DiaryConroller extends BaseController{
	
	@Autowired
	private DiaryService diaryService;
	
	/**
	 * 
	 * @param request
	 * @param pageNum  页数
	 * @param classify 分类
	 * @return
	 */
	@GetMapping("/list.re")
	@IgnoreSecurity
	public ResponseResult<PageInfo<Diary>>  diaryList(
			HttpServletRequest request,
			DiaryForm form) {
		ResponseResult<PageInfo<Diary>> response = new ResponseResult<>();		
		Integer adminId = currentAdminId(request);
		form.setCreator(adminId);
		form.setDelflag(DeleteFlag.VALID.getCode());
		PageInfo<Diary> page = diaryService.getPage(form);	
		return response.success(page);
	}
	
	
	/**
	 * 创建或者保存修改一篇日记
	 * @param request
	 * @return
	 */
	@PostMapping("/addOrUpdate.do")
	@IgnoreSecurity
	public ResponseResult<Diary>  createDiary(HttpServletRequest request,
			@RequestBody(required=true) DiaryForm diary) {
		
		ResponseResult<Diary> response = new ResponseResult<>();
		
		if (StringUtil.isEmpty(diary.getTitle()) || StringUtil.isEmpty(diary.getContent())) {
			return response.fail("标题或者内容不能为null");
		}
		Integer adminId = currentAdminId(request);
		diary.setModifier(adminId);
		if (diary.getId() == null) {
			diary.setCreator(adminId);
			diaryService.add(diary);			
		 } else {
			 if(diaryService.update(diary) != 1) {
				 return response.fail("更新失败");
			 }
		 }
		return response.success(diaryService.getById(diary));
		
	}
	
	@DeleteMapping("/s/{id}/delete.do")
	@IgnoreSecurity
	public ResponseResult<Diary> deleteDiary(HttpServletRequest request,
			@PathVariable(name="id") Integer id) {
		
		ResponseResult<Diary> response = new ResponseResult<>();
		Diary diary = new Diary(id);
		diary.setModifier(currentAdminId(request));
		if (diaryService.delete(diary)) {
			return response.success();
		}
		return response.fail("删除异常");
		
	}
	
	

}
