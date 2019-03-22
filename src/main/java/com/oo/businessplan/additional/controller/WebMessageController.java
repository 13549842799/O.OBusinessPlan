package com.oo.businessplan.additional.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.additional.pojo.WebMessage;
import com.oo.businessplan.additional.service.WebMessageService;
import com.oo.businessplan.common.pageModel.ResponseResult;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/webMessage")
public class WebMessageController {
	
	@Autowired
	private WebMessageService wmservice;
	
	@ApiOperation(value = "获取目标网站")
	@RequestMapping(value="/target.do",method=RequestMethod.GET)
	public ResponseResult<WebMessage> getTarget(
			@ApiParam(value = "网站编号", required = true)@RequestParam(value="code")String  code){
		   WebMessage web = wmservice.selectWeb(code);
		   ResponseResult<WebMessage> response = new ResponseResult<>();
		   if (web!=null) {
			 return response.success(web);
		   }
		   return response.fail("不存在此网站");
	}
	
	@ApiOperation(value = "网站列表")
	@RequestMapping(value="/weblist.do",method=RequestMethod.GET)
	public ResponseResult<List<WebMessage>> webList(){
		List<WebMessage> webs = wmservice.allWeb();		
		ResponseResult<List<WebMessage>> response = new ResponseResult<>();
		return response.success(null,webs);
	     
	}

}
