package com.oo.businessplan.article.controller;

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

import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.pojo.entity.FinalReport;
import com.oo.businessplan.article.service.FinalReportService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.AuthorityNotEnoughException;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;

@RequestMapping("/api/article/finalresport")
@RestController
public class FinalReportController extends BaseController {
	
	@Autowired
	private FinalReportService service;
	
	@GetMapping("/s/{id}/report.re")
	@IgnoreSecurity
	public ResponseResult<FinalReport>  findReport(
			HttpServletRequest request,
			@PathVariable(value="id")int id) {
		ResponseResult<FinalReport> response = new ResponseResult<>();
		FinalReport report = service.getById(new FinalReport(id));
		if (report == null) {
			return response.fail("找不到目标日记");
		}
		if (report.getCreator() != currentAdminId(request)) {
			throw new AuthorityNotEnoughException();
		}
	
		return response.success(report);
	}
	
	/**
	 * 
	 * @param request
	 * @param pageNum  页数
	 * @param classify 分类
	 * @return
	 */
	@GetMapping("/list.re")
	@IgnoreSecurity
	public ResponseResult<PageInfo<FinalReport>>  finalReportList(
			HttpServletRequest request,
			FinalReport report,
			Integer pageNum,
			Integer PageSize) {
		ResponseResult<PageInfo<FinalReport>> response = new ResponseResult<>();		
		Integer adminId = currentAdminId(request);
		report.setCreator(adminId);
		report.setDelflag(DeleteFlag.VALID.getCode());
		PageInfo<FinalReport> page = service.getPage(report, pageNum, PageSize);	
		return response.success(page);
	}
	
	
	/**
	 * 创建或者保存修改一篇总结
	 * @param request
	 * @return
	 */
	@PostMapping("/addOrUpdate.do")
	@IgnoreSecurity
	public ResponseResult<FinalReport>  createFinalReport(HttpServletRequest request,
			@RequestBody(required=true) FinalReport report) {
		
		ResponseResult<FinalReport> response = new ResponseResult<>();
		
		if (StringUtil.isEmpty(report.getTitle())) {
			return response.fail("标题不能为空");
		}
		Integer adminId = currentAdminId(request);
		report.setModifier(adminId);
		if (report.getId() == null) {
			report.setCreator(adminId);
			service.add(report);			
		 } else {
			 if(service.update(report) != 1) {
				 return response.fail("更新失败");
			 }
		 }
		return response.success(service.getById(report));
		
	}
	
	@DeleteMapping("/s/{id}/delete.do")
	@IgnoreSecurity(authority = false)
	public ResponseResult<FinalReport> deleteFinalReport(HttpServletRequest request,
			@PathVariable(name="id") Integer id) {
		
		ResponseResult<FinalReport> response = new ResponseResult<>();
		FinalReport report = new FinalReport(id);
		report.setCreator(currentAdminId(request));
        report.setModifier(currentAdminId(request));
		if (service.delete(report)) {
			return response.success();
		}
		return response.fail("删除异常");
		
	}
	
	@GetMapping("/searchTitle.re")
	@IgnoreSecurity(authority = false)
	public ResponseResult<List<FinalReport>> searchDiaryTitle(HttpServletRequest request,
			@RequestParam(name="title") String title) {
		
		ResponseResult<List<FinalReport>> response = new ResponseResult<>();
		
		return response.success(service.searchTitle(title));
		
	}

}
