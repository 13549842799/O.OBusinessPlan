package com.oo.businessplan.article.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.pojo.entity.Diary;
import com.oo.businessplan.article.pojo.form.DiaryForm;
import com.oo.businessplan.article.service.DiaryService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.enumeration.StatusFlag;
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
			@RequestParam(value="pageNum", defaultValue="1")Integer pageNum,
			@RequestParam(value="classify")Integer classify,
			@RequestParam("author") Integer author) {
		
		ResponseResult<PageInfo<Diary>> response = new ResponseResult<>();		
		Integer adminId = currentAdminId(request);

		/*
		 * 如果想要查询的日记的所属作者不等于当前登录约的用户，那么要验证当前用户是否拥有查看他人日记的权限
		 */
		if (!adminId.equals(author)) {
			
		}
		
		PageInfo<Diary> page = diaryService.getPage(pageNum, adminId, classify);
		
		return response.success(page);
	}
	
	
	/**
	 * 创建或者保存修改一篇日记
	 * @param request
	 * @return
	 */
	@PostMapping("/add.do")
	@IgnoreSecurity
	public ResponseResult<Diary>  createDiary(HttpServletRequest request,
			@RequestBody(required=true) DiaryForm diary) {
		
		ResponseResult<Diary> response = new ResponseResult<>();
		
		if (StringUtil.isEmpty(diary.getTitle()) || StringUtil.isEmpty(diary.getContent())) {
			return response.fail("标题或者内容不能为null");
		}
		Integer adminId = currentAdminId(request);
		diary.setCreator(adminId);
		diary.setModifier(adminId);
		diary.setState(StatusFlag.ENABLE.getCode());
		
		try {
			diaryService.add(diary);
			return response.success(diaryService.getById(diary));
		} catch (AddErrorException e) {
			e.printStackTrace();
			return response.error(e.getMessage());
		}
	}

}
