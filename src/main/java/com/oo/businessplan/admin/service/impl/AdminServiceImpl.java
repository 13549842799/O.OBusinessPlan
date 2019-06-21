package com.oo.businessplan.admin.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.additional.pojo.WebMessage;
import com.oo.businessplan.admin.mapper.AdminMapper;
import com.oo.businessplan.admin.mapper.EmployeeMapper;
import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.admin.pojo.entity.Employee;
import com.oo.businessplan.admin.pojo.form.AdminForm;
import com.oo.businessplan.admin.pojo.page.Padmin;
import com.oo.businessplan.admin.service.AdminService;
import com.oo.businessplan.admin.service.EmployeeService;
import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.Role;
import com.oo.businessplan.authority.service.AuthorityService;
import com.oo.businessplan.authority.service.RoleService;
import com.oo.businessplan.basic.mapper.BaseMapper;
import com.oo.businessplan.basic.service.MsgService;
import com.oo.businessplan.basic.service.RedisCacheService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.basic.service.support.RedisCacheSupport;
import com.oo.businessplan.common.constant.EntityConstants;
import com.oo.businessplan.common.constant.ResultConstant;
import com.oo.businessplan.common.constant.SystemKey;
import com.oo.businessplan.common.enumeration.AuthorityCode;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.enumeration.MsgTypeCode;
import com.oo.businessplan.common.enumeration.StatusFlag;
import com.oo.businessplan.common.exception.AuthorityNotEnoughException;
import com.oo.businessplan.common.exception.NullUserException;
import com.oo.businessplan.common.exception.ObjectExistException;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.exception.login.PasswordValidException;
import com.oo.businessplan.common.redis.RedisTokenManager;
import com.oo.businessplan.common.security.SessionManager;
import com.oo.businessplan.common.util.PassUtil;
import com.oo.businessplan.common.util.StringUtil;

@Service("adminService")
public class AdminServiceImpl extends RedisCacheSupport<Admin> implements AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private RedisCacheService<Employee> redisEmp ;
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private MsgService msgService;
	
	

	@Override
	public int update(Admin t) {
		int result = super.update(t);
		if (result == 1) {
			this.saveObject(t.getAccountname(), t, EXPIRED, TIMEUNIT);
		}
		return result;
	}

	@Override
	public Map<String, Object> getAdminByAccountName(String accountName) {
		
		Map<String,Object> result = new HashMap<>();
		
		if (StringUtil.isEmpty(accountName)) {
			 result.put(SystemKey.ERROR_KEY,"请输入id或者账号用户名");
			 return result;
		}	
		//判断状态
		Admin admin = getObject(accountName,EXPIRED,TIMEUNIT);
		if (admin==null) {
			result.put(SystemKey.ERROR_KEY,"该账号不存在或已被禁用");
			return result;
		}
		admin.setPassword(null);

        result.put("admin", admin);
		
		return result;
	}

	@Override
	public boolean checkNikenameExist(String nikename) {
		
		return adminMapper.validNikeNameExist(nikename,DeleteFlag.VALID.getCode())>0;
	}

	@Override
	public Map<String,String> alterPassword(String oldPass, String newPass, String verificationCode, String phoneNo) {
		
		Map<String,String> result = new HashMap<>();
		
		//1.检验验证码是否匹配
		Map<String,String> isValid = msgService.validMsg(phoneNo, verificationCode,MsgTypeCode.ALTER.getCode());
		if (isValid.get(SystemKey.ERROR_KEY)!=null) {
			return isValid;
		}
		//2.判断密码是否相同
		if (!oldPass.equals(newPass)) {
			result.put(SystemKey.ERROR_KEY,"两次密码不相同");
			return result;
		}
		return result;
	}

	@Override
	public PageInfo<Padmin> getAdminList(AdminForm adminForm) {
		
		PageHelper.startPage(adminForm.getPageNum(), adminForm.getPageSize());
		List<Padmin> list = adminMapper.getListByForm(adminForm);
		PageInfo<Padmin> page = new PageInfo<>(list);
		return page;
	}

	@Override
	public Admin registAdmin(String employeeCode,Integer creatorId) throws ObjectNotExistException, ObjectExistException, NoSuchAlgorithmException, UnsupportedEncodingException {
		
		//判断是否存在此职员	
		Employee employee = redisEmp.getObject(employeeCode, EXPIRED, TIMEUNIT);
		if (employee==null) {
			throw new ObjectNotExistException(ResultConstant.NOT_EXIST_EMPLOYEE);
		}
		Admin param_admin = null ;
		/*param_admin.setRelated(employee);
		param_admin.setDelflag(DeleteFlag.VALID.getCode());
		Admin admin = adminMapper.findAdmin(param_admin);*/
		param_admin = getObject(employeeCode, EXPIRED, TIMEUNIT);
		if (param_admin!=null) {
			throw new ObjectExistException(ResultConstant.EMPLOYEE_EXIST_ADMIN);
		}
		//创建账号
		param_admin = new Admin();
		param_admin.setAccountname(employeeCode);
		param_admin.setPassword(PassUtil.getEncryptedPwd(employeeCode));
		param_admin.setNikename(employee.getName());
		param_admin.setAvatar(employee.getAvatar());
		param_admin.setRelatedid(employee.getId());
		adminMapper.add(param_admin);
		//赋予普通职员的角色
		roleService.giveRole(new int[]{2},param_admin.getId(), creatorId);
		
		return param_admin;
	}

	@Override
	public void changeAdminState(int adminId, byte state, long id) throws ObjectNotExistException {
		
		int count = adminMapper.state(state,new Long(id).intValue());
		if (count==0) {
			throw new ObjectNotExistException(ResultConstant.NOT_EXIST_ADMIN);
		}
		
	}
	
	@Override
	public Admin getAdminByAccount(String account, WebMessage web) throws NullUserException,AuthorityNotEnoughException {

		//判断账号是否存在
		Admin admin = getObject(account,EXPIRED,TIMEUNIT);
		if (admin==null) {
			throw new NullUserException();
		}
		
		//1.判断登陆权限
        if (web != null && !authorityService.checkLoginToWebAble(account, web)) {
			throw new AuthorityNotEnoughException();
		}
		return admin;
	}
	
	@Override
	public boolean checkPasswordValid(String dbPassowrd , String password) {
		try {
			return PassUtil.validPassword(password,dbPassowrd);		
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} 	
	}
	
	@Override
	@Transactional
	public Admin generalByECode(String userName, WebMessage webMessage) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//如果目标网站不是主站，则返回nul
		if (!AuthorityCode.LOGIN_CENTER.getCode().equals( webMessage.getCode())) {
			return null;
		}
		//判断是否存在编号为此用户名的员工
		Employee employee = redisEmp.getObject(userName, EXPIRED, TIMEUNIT);
		if (employee==null) {
			return null;
		}
		//判断此员工是否已经存在账号了
		//Admin admin = new Admin();
		Admin admin = getObject(userName, EXPIRED, TIMEUNIT);
		/*admin.setRelated(employee);
		admin.setDelflag(DeleteFlag.VALID.getCode());
		admin = adminMapper.findAdmin(admin);*/
		if (admin!=null) {
			return null;
		}
		//如果此员工没有账号，则自动注册一个账号
		admin = new Admin();
		admin.setAccountname(userName);
		admin.setPassword(PassUtil.getEncryptedPwd(userName));
		admin.setNikename(userName);
		admin.setAvatar(employee.getAvatar());
		admin.setRelatedid(employee.getId());
		adminMapper.add(admin);
		//职员关联账号
		employee.setAdminId(admin.getId());
		employeeMapper.update(employee);
		//如果这个账号是整个系统中的第一个账号，则默认为超级管理员
		int adminNum = adminMapper.searchCount(null, DeleteFlag.VALID.getCode());
		if (adminNum==1) {
			roleService.giveRole(new int[]{1}, admin.getId(), null);
		}else {
			roleService.giveRole(new int[]{2}, admin.getId(), null);
		}
		
		
		return admin;
	}

	@Override
	public boolean removeAdmin(String accountName) {
		
		return remove(accountName);
	}
	
	

}
