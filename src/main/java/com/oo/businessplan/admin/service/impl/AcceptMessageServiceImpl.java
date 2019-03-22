package com.oo.businessplan.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.admin.mapper.AcceptMessageMapper;
import com.oo.businessplan.admin.pojo.entity.AcceptMessage;
import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.admin.service.AcceptMessageService;
import com.oo.businessplan.admin.service.AdminService;
import com.oo.businessplan.basic.service.RedisCacheService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.enumeration.DeleteFlag;

@Service("acceptMessageService")
public class AcceptMessageServiceImpl extends BaseServiceImpl<AcceptMessage> implements AcceptMessageService {

	@Autowired
	AcceptMessageMapper amMapper;
	
	@Autowired
	AdminService adService;
	
	@Autowired
	RedisCacheService<Admin> redisAdmin;
	
	
	public AcceptMessage getObject(String key, int expired, int timeUnit) {
		
		return null;
	}

	public List<AcceptMessage> getListObject(String key, int expired, int timeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getAdminMessage(String account, Byte messageType, Byte state,
			Integer pageNum, Integer pageSize) {
		
		Admin admin = redisAdmin.getObject(account, 
				RedisCacheService.EXPIRED, RedisCacheService.TIMEUNIT);
		System.out.println(admin.getId());
		
		PageHelper.startPage(pageNum, pageSize, false);//分页工具，不查询总页数
		List<Map<String,Object>> list = amMapper.getAdminMessage(admin.getId(), 
				messageType, state, DeleteFlag.VALID.getCode());
		//获取页面对象，可以从中获取最大页数等等信息
		//PageInfo<Map<String,Object>> page = new PageInfo<>(list);
 		
		return list;
	}

}
