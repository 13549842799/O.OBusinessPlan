package com.oo.businessplan.article.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.article.pojo.entity.Classify;
import com.oo.businessplan.article.service.ClassifyService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.constant.ResultConstant;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.AddErrorException;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;

/**
 * 文章分类的控制器
 * 专门管理系统的所有文章模块的分类。
 * 在这个模块下还有用户自身的权限模块和管理员模块
 * 即用户创建自定义分类和管理员创建的系统分类
 * @author cyz
 *
 */
@RequestMapping("/api/article/classify")
@RestController
public class ClassifyController extends BaseController {
	
	@Autowired
	private ClassifyService clsService;
	
	/**
	 * 用户获取自身的对应的分类
	 * @param request
	 * @param type
	 * @param childType
	 * @return
	 */
	@GetMapping("/admin/list.re")
	@IgnoreSecurity
	public ResponseResult<List<Classify>> classifyList(
			HttpServletRequest request,
			@RequestParam(value="type")Byte type,
			@RequestParam(value="childType")Byte childType) {
		
		ResponseResult<List<Classify>> response = new ResponseResult<>();
		
		Classify cls = new Classify();
		cls.setType(type);
		cls.setChildType(childType);
		cls.setDelflag(DeleteFlag.VALID.getCode());
		List<Classify> list = clsService.getList(cls);
		
		return response.success(list);
	}
	
	/**
	 * 用户创建自定义分类
	 * @param request
	 * @param type
	 * @param childType
	 * @return
	 */
	@PostMapping("/admin/add.do")
	@IgnoreSecurity
	public ResponseResult<Classify> classifyAdd(
			HttpServletRequest request,
			@RequestBody Classify cls) {
		
		ResponseResult<Classify> response = new ResponseResult<>();
		
		if (cls.getType() == null || cls.getChildType() == null || StringUtil.isEmpty(cls.getName())) {
			return response.fail(ResultConstant.PARAMETER_REQUIRE_NULL);
		}		
		
		clsService.add(cls);
		
		return response.success(cls);
	}
	
	
	
}
