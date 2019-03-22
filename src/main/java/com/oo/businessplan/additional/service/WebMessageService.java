package com.oo.businessplan.additional.service;

import java.util.List;

import com.oo.businessplan.additional.pojo.WebMessage;

public interface WebMessageService {
	
	/**
	 * 确认目标网站的主页
	 * @param code
	 * @return
	 */
	WebMessage selectWeb(String code);
	
	/**
	 * 所有的网站信息的集合
	 * @return
	 */
	List<WebMessage> allWeb();

}
