package com.oo.businessplan.article.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

import com.oo.businessplan.article.pojo.entity.Classify;
import com.oo.businessplan.article.service.ClassifyService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.constant.ResultConstant;
import com.oo.businessplan.common.enumeration.DeleteFlag;
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
	@GetMapping("/list.re")
	@IgnoreSecurity
	public ResponseResult<List<Classify>> classifyList(
			HttpServletRequest request,
			@RequestParam(value="type")Byte type,
			@RequestParam(value="childType")Byte childType) {
		
		ResponseResult<List<Classify>> response = new ResponseResult<>();
		
		Classify cls = new Classify();
		cls.setCreator(currentAdminId(request));
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
	@PostMapping("/add.do")
	@IgnoreSecurity
	public ResponseResult<Classify> classifyAdd(
			HttpServletRequest request,
			@RequestBody Classify cls) {			
		cls.setType(Classify.CUSTOMCLASSIFY);
		cls.setCreator(currentAdminId(request));
		return addCommon(cls);
	}
	
	/**
	 * 用户创建自定义分类
	 * @param request
	 * @param type
	 * @param childType
	 * @return
	 */
	@PostMapping("/system/add.do")
	@IgnoreSecurity
	public ResponseResult<Classify> classifyAddBySystem(
			HttpServletRequest request,
			@RequestBody Classify cls) {
		cls.setType(Classify.SYSTEMCLASSIFY);
		return addCommon(cls);
	}
	
	private ResponseResult<Classify> addCommon(Classify cls) {
		ResponseResult<Classify> response = new ResponseResult<>();
		if (cls.getChildType() == null || StringUtil.isEmpty(cls.getName())) {
			return response.fail(ResultConstant.PARAMETER_REQUIRE_NULL);
		}		
		cls.setCreateTime(new Timestamp(new Date().getTime()));
		clsService.add(cls);
		
		return response.success(cls);
	}
	
	/**
	 * 编辑自定义分类 此接口是编辑用户自定义分类的接口，所以type参数不允许传系统值
	 * @param request
	 * @param cls
	 * @return
	 */
	@PostMapping("/edit.do")
	@IgnoreSecurity
	public ResponseResult<Classify> classifyUpdate(
			HttpServletRequest request,
			@RequestBody Classify cls) {
		cls.setCreator(currentAdminId(request));
		return updarteCommon(request, cls, 1);
	}
	
	/**
	 * 编辑分类 此接口是拥有权限的管理员编辑分类的接口
	 * @param request
	 * @param cls
	 * @return
	 */
	@PostMapping("/system/edit.do")
	@IgnoreSecurity
	public ResponseResult<Classify> classifyUpdateBySystem(
			HttpServletRequest request,
			@RequestBody Classify cls) {
		cls.setCreator(currentAdminId(request));
		return updarteCommon(request, cls, 1);
	}
	
	/**
	 * 
	 * @param request
	 * @param cls
	 * @param user 当前操作的用户类型  管理员操作-0  用户操作-1
	 * @return
	 */
	private ResponseResult<Classify> updarteCommon(
			HttpServletRequest request,
			@RequestBody Classify cls,
			int user) {
		ResponseResult<Classify> response = new ResponseResult<>();
		if ((user == 1 && cls.getType() == Classify.SYSTEMCLASSIFY) || cls.getChildType() == null || StringUtil.isEmpty(cls.getName())) {
			return response.fail(ResultConstant.PARAMETER_REQUIRE_NULL);
		}
		if (cls.getId() == null) {
			return response.error(ResultConstant.SELECT_OBJECT);
		}
		int result = clsService.update(cls);
		return response.updateResult(result);
	}
	
	/**
	 * 删除自定义
	 * @param request
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}/delete.do")
	@IgnoreSecurity
	public ResponseResult<Classify> classifyDelete(HttpServletRequest request,
			@PathVariable("id")int id) {
		ResponseResult<Classify> response = new ResponseResult<>();
		Classify cls = new Classify();
		cls.setId(id);
		cls.setCreator(currentAdminId(request));
		cls.setDelflag(DeleteFlag.DELETE.getCode());
		
		boolean result = clsService.delete(cls);
		response.deleteResult(result);
		if (result) {
			//检查当前分类下是否存在文章，如果存在，则移入默认分类中(默认系统分类id = 1)
			if (clsService.checkArticleExists(cls)) {
				clsService.moveArticleFromOldClassify(cls, 1);
			}
		}		
		return response;
	}
	
	
	@GetMapping("/{id}/valid/count.re")
	@IgnoreSecurity
	public ResponseResult<Integer> classifyCheckArticles(HttpServletRequest request,
			@PathVariable("id")int id) {
		ResponseResult<Integer> response = new ResponseResult<>();
		Classify cls = new Classify();
		cls.setId(id);
		cls.setCreator(currentAdminId(request));
		cls.setDelflag(DeleteFlag.DELETE.getCode());			
		return response.success(clsService.articleCountOfClassify(cls));
	}
	
	
	
	
	
	
}
