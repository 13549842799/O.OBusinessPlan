package com.oo.businessplan.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.valid.ValidService;

@RestController
@RequestMapping("/test")
public class TestController {
	
	//@Autowired
	//SecurityConfig config;
	
    
	@GetMapping("/test.re")
	public ResponseResult<Object> test () {

		return new ResponseResult<>().success("成功");
	}
}
