package com.oo.businessplan.additional.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oo.businessplan.additional.mapper.WebMessageMapper;
import com.oo.businessplan.additional.pojo.WebMessage;
import com.oo.businessplan.additional.service.WebMessageService;

@Service("WebMessageServiceImpl")
public class WebMessageServiceImpl implements WebMessageService {

	@Autowired
	private WebMessageMapper wmMapper;
	
	@Override
	public WebMessage selectWeb(String code) {
		return wmMapper.selectWeb(code);
	}

	@Override
	public List<WebMessage> allWeb() {
		return wmMapper.findWebs();
	}

}
