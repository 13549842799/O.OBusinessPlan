package com.oo.businessplan.admin.service;

import java.util.List;
import java.util.Map;

import com.oo.businessplan.admin.pojo.entity.AcceptMessage;
import com.oo.businessplan.basic.service.BaseService;

public interface AcceptMessageService extends BaseService<AcceptMessage> {

	  /**
	   * 获取消息相关信息
	   * @param account 账号名
	   * @param messageType 消息的类型
	   * @param state 消息的状态
	   * @param pageNum 
	   * @param pageSize
	   * @return
	   */
	  List<Map<String,Object>> getAdminMessage(String account,Byte messageType,
			  Byte state,Integer pageNum,Integer pageSize);
}
