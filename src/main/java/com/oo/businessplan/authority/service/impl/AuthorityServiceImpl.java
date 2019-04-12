package com.oo.businessplan.authority.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.oo.businessplan.additional.pojo.WebMessage;
import com.oo.businessplan.authority.mapper.AuthorityMapper;
import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.AuthorityWithKey;
import com.oo.businessplan.authority.service.AuthorityService;
import com.oo.businessplan.basic.service.UtilService;
import com.oo.businessplan.basic.service.support.RedisCacheSupport;
import com.oo.businessplan.common.constant.EntityConstants;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.enumeration.StatusFlag;
import com.oo.businessplan.common.util.StringUtil;

@Service("authorityService")
public class AuthorityServiceImpl extends RedisCacheSupport<Authority>
                     implements AuthorityService, UtilService<Authority>{

	@Autowired
	private AuthorityMapper authorityMapper;
	 
	@Override
	@Transactional
	public boolean insertOrUpdateForBatch(List<Authority> list) {
		try {
			authorityMapper.insertOrUpdate(list);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		    return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Byte> getKeyMap(String account) {
		
		Object objKey = tokenManager.getValueFromMap(account, 
				EntityConstants.REDIS_AUTHORITY_Map_NAME,EXPIRED, TIMEUNIT);
		if ( objKey == null ) {
			return resetAuthsRedis(account);
		}
		
		return (Map<String, Byte>) objKey;
	}
	
	@Override
	public Map<String, Byte> resetAuthsRedis(String account) {
		List<Authority> auths = authorityMapper.getListByStr(account,
				DeleteFlag.VALID.getCode(),StatusFlag.ENABLE.getCode());
		Map<String, Byte> authMap = new HashMap<>();
		if ( auths != null && auths.size()>0) {
			AuthorityWithKey temp = null;
			for (int i = 0,len = auths.size(); i < len; i++) {
				temp = (AuthorityWithKey)auths.get(i);
				authMap.put(temp.getKey(), temp.getLevel());
			}
			authMap.remove(null);//移除key为null的键值对
			tokenManager.saveForMap(account, EntityConstants.REDIS_AUTHORITY_Map_NAME,
					authMap, EXPIRED, TIMEUNIT);
			return authMap;
		}
		return authMap;
	}

	@Override
	public List<Authority> getListByAccountAndType(Integer adminId, Byte type) {
		
		return authorityMapper.getAuthoritiesByAdminIdAndType(adminId, type, DeleteFlag.VALID.getCode(), StatusFlag.ENABLE.getCode());
	}

	/**
	 * 核心思路：因为分配给网站的编号即是权限的编号，又因为子权限的编号是以父权限
	 *          的编号做开头，所以只要判断网站编号是否相同或者是否与开头符合。
	 * 算法：首先判断web对象中是否存在有效的编号，如果没有则返回false，
	 * 获取目标的权限列表，然后进行遍历，判断是否符合
	 * 
	 * 新的算法：获取key的集合，key是一个标志用以在权限检验中作为是否符合此权限的标志
	 * 
	 */
	@Override
	public boolean checkLoginToWebAble(String account, WebMessage web) {
		String webCode = web.getCode();
		if (StringUtil.isEmpty(webCode)) {
			return false;
		}
		Map<String, Byte> map = getKeyMap(account);
		System.out.println(map);
		return map != null && !map.isEmpty() && map.containsKey(webCode);

	}

	@Override
	public List<Authority> getAuthorities(Integer roId, int[] reIds) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("roId", roId);
		params.put("reIds", reIds);
		return authorityMapper.getAuthorities(params);
	}

	@Override
	public String createKey(Authority obj) {
		
		return obj.getRoid()+"_"+obj.getReid();
	}

	@Override
	public List<AuthorityWithKey> getfullList(int roleId) {
		
		List<AuthorityWithKey> list = authorityMapper
				.getAuthorityWithResourceByRole(roleId, DeleteFlag.VALID.getCode());
		System.out.println(list.size());
		list = getResourceTree(list, null);
		System.out.println(list);
		return list;
	}
	
    public List<AuthorityWithKey> getResourceTree(List<AuthorityWithKey> all, AuthorityWithKey current) {
		
		List<AuthorityWithKey> childs = new ArrayList<>();
		AuthorityWithKey node = null;
		for (int i = 0, len = all.size(); i < len; i++) {
			node = all.get(i);
			if ((current == null && node.getrPid() == null)
					|| (current != null && Objects.equals(current.getRid(), node.getrPid()))) {
				childs.add(node);
				node.setChilds(getResourceTree(all, node));			
			}
		}
		
		return childs;
	}
	
	

}
