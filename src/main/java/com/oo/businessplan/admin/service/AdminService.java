package com.oo.businessplan.admin.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.oo.businessplan.additional.pojo.WebMessage;
import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.admin.pojo.form.AdminForm;
import com.oo.businessplan.admin.pojo.page.Padmin;
import com.oo.businessplan.basic.service.BaseService;
import com.oo.businessplan.common.exception.AuthorityNotEnoughException;
import com.oo.businessplan.common.exception.NullUserException;
import com.oo.businessplan.common.exception.ObjectExistException;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.exception.login.PasswordValidException;

public interface AdminService extends BaseService<Admin>{
	
	/**
	 * 从数据库中查询对应用户名的admin记录
	 * @param accountName 用户名
	 * @return
	 */
	Admin getAdminByName(String accountName);
	
	/**
	 * 获取缓存的admin，如果没有缓存，则从数据库中获取
	 * @param accountName 账号用户名
	 * @return
	 */
	Map<String,Object> getAdminByAccountName(String accountName);

	/**
	 * 从缓存中移除对应的session
	 * @param accountName 用户名
	 * @param isPhone  是否移除手机的session
	 * @return
	 */
	boolean removeAdmin(String accountName, boolean isPhone);
	/**
	 * 判断此的昵称是否存在
	 * @param nikename
	 * @return
	 */
	boolean checkNikenameExist(String nikename);

	/**
	 * 修改密码
	 * @param oldPass 旧密码
	 * @param newPass 新密码
	 * @return  1:正确 -1:两次密码不相同 0-密码不正确
	 */
	int alterPassword(String oldPass, String newPass);

	PageInfo<Padmin> getAdminList(AdminForm adminForm);

	Admin registAdmin(String employeeCode,Integer creator) throws ObjectNotExistException,ObjectExistException, NoSuchAlgorithmException, UnsupportedEncodingException;

	void changeAdminState(int adminId, byte state, long id) throws ObjectNotExistException;

	Admin getAdminByAccount(String account,WebMessage web) throws NullUserException,AuthorityNotEnoughException;

	boolean checkPasswordValid(String dbpassword,String password);

	Admin generalByECode(String userName, WebMessage webMessage) throws NoSuchAlgorithmException, UnsupportedEncodingException; 
	
}
