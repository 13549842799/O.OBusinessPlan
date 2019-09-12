package com.oo.businessplan.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.common.pageModel.ResponseResult;

@RestController
@RequestMapping("/test")
public class TestController {
	
	//@Autowired
	//SecurityConfig config;
    
	@GetMapping("/test.re")
	public ResponseResult<Object> test () {
		System.out.println("test.re");
		return new ResponseResult<>().success("成功");
	}
}
