package com.oo.businessplan.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.common.pageModel.ResponseResult;

@RestController
@RequestMapping("/system")
public class SystemController {

	@GetMapping("/app/version.re")
	public ResponseResult<String> checkEdition() {
		return null;
	}
}
