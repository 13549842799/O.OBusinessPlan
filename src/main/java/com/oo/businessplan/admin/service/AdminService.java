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
	 * 获取admin
	 * @param accountName 账号用户名
	 * @return
	 */
	Map<String,Object> getAdminByAccountName(String accountName);

	boolean removeAdmin(String accountName);
	/**
	 * 判断此的昵称是否存在
	 * @param nikename
	 * @return
	 */
	boolean checkNikenameExist(String nikename);

	Map<String, String> alterPassword(String oldPass, String newPass, String verificationCode, String phoneNo);

	PageInfo<Padmin> getAdminList(AdminForm adminForm);

	Admin registAdmin(String employeeCode,Integer creator) throws ObjectNotExistException,ObjectExistException, NoSuchAlgorithmException, UnsupportedEncodingException;

	void changeAdminState(int adminId, byte state, long id) throws ObjectNotExistException;

	Admin getAdminByAccount(String account,WebMessage web) throws NullUserException,AuthorityNotEnoughException;

	boolean checkPasswordValid(String dbpassword,String password);

	Admin generalByECode(String userName, WebMessage webMessage) throws NoSuchAlgorithmException, UnsupportedEncodingException; 
	
}
