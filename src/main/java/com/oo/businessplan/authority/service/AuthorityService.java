package com.oo.businessplan.authority.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.oo.businessplan.additional.pojo.WebMessage;
import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.AuthorityWithKey;
import com.oo.businessplan.basic.service.BaseService;

public interface AuthorityService extends BaseService<Authority>{
	
	/**
	 * 批量插入或者更新权限记录
	 * @param list
	 */
	boolean insertOrUpdateForBatch(List<Authority> list);
	
	/**
	 * 判断用户名为account的账号是否拥有登陆web指向的网站的权限
	 * @param account
	 * @param web
	 * @return
	 */
	boolean checkLoginToWebAble(String account,WebMessage web);
	
	/**
	 * 获取用户名为account的对象所持有的资源的key的set集合
	 * @param account
	 * @return
	 */
	Map<String, Byte> getKeyMap(String account);
	
	/**
	 * 根据当前的用户和权限类型参数获取对应的权限列表
	 * @param adminId
	 * @param type
	 * @return
	 */
	List<Authority> getListByAccountAndType(Integer adminId, Byte type);
	
	/**
	 * 通过一下两个参数获取对应的权限列表（获取某个角色的指向特定资源的权限组）
	 * @param roId 角色id
	 * @param reId 资源id数组
	 * @return
	 */
	List<Authority> getAuthorities(Integer roId, int[] reIds);

	List<AuthorityWithKey> getfullList(int roleId);
	
}
