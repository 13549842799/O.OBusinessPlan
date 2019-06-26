package com.oo.businessplan.admin.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oo.businessplan.admin.mapper.EmployeeMapper;
import com.oo.businessplan.admin.pojo.entity.Employee;
import com.oo.businessplan.admin.service.EmployeeService;
import com.oo.businessplan.basic.service.support.RedisCacheSupport;
import com.oo.businessplan.common.constant.ResultConstant;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.exception.PatternErrorException;
import com.oo.businessplan.common.pageModel.MethodResult;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.common.util.UpLoadUtil;

@Service("employeeService")
public class EmployeeServiceImpl extends RedisCacheSupport<Employee> implements EmployeeService {
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Autowired
	private UpLoadUtil upLoadUtil;
	
	@Override
	@Transactional
	public void addEmployee(Employee employee) throws PatternErrorException, UnsupportedEncodingException, NoSuchAlgorithmException {
		
		//1.校检employee中的数据是否合法		
		//1.1校检名称		
		//1.2校检性别
		if (employee.getSex()!=null&&(!employee.getSex().equals("男")||!employee.getSex().equals("女"))) {
			throw new PatternErrorException(ResultConstant.PATTERN_SEX_ERROR);
		}	
		//1.3校检身份证号码
		if (employee.getIdCard()!=null&&!StringUtil.isIdCard(employee.getIdCard())) {
			throw new PatternErrorException(ResultConstant.PATTERN_IDCARD_ERROR);
		}	
		//1.4手机号校检
		if (employee.getPhone()!=null) {
			if (employee.getPhone().length()!=11) {
				throw new PatternErrorException(ResultConstant.PATTERN_PHONE_LENGTH_ERROR);
			}
			if (!StringUtil.isPhone(employee.getPhone())) {
				throw new PatternErrorException(ResultConstant.PATTERN_PHONE__ERROR);
			}		
		}
		//1.5校检邮箱
		if (!StringUtil.isEmail(employee.getEmail())) {
			throw new PatternErrorException(ResultConstant.PATTERN_EMAIL_ERROR);
		}
		//2.插入职员记录
		employeeMapper.add(employee);//此处存在触发器，部门表人数字段自动增1
		//3.插入支援编号
		//3.1生成支援编号  转到触发器中进行  并入部门人数增加触发器中
		/*int eid = employee.getEid();
		String eCode = "OB"+eid;
		Employee emCode = new Employee();
		emCode.setEid(eid);
		emCode.setEcode(eCode);
		//3.2更新编号
		employeeMapper.updateEcode(eCode, eid);*/
	}

	@Override
	public void upLoadAvatar(HttpServletRequest request,int employeeId, Integer modifier) throws ObjectNotExistException {
		
		Map<String,Map<String,String>> params = new HashMap<>();
  	    Map<String,String> config = new HashMap<>();
  	    config.put("type","img");
  	    config.put("newName", null);
  	    config.put("targetPath","");
  	    params.put("avatar", config);	    	  
  	    MethodResult<Map<String, String>> result = upLoadUtil.uploadFile(request, params);
  	    if (result.fail()) {
			return;
		}
  	    String newPath = result.getData().get("avatar");
  	    if (newPath==null) {
		    throw new ObjectNotExistException(ResultConstant.AVATAR_UPLOAD_ERROR);
	    }
  	    Employee employee = new Employee();
  	    employee.setId(employeeId);
  	    employee.setAvatar(newPath);
  	    employee.setDelflag(DeleteFlag.VALID.getCode());
  	    employeeMapper.update(employee);
		
	}

	@Override
	public Employee getByAdmin(int adminId, String accountName, int type) {
		Map<String, Object> otherParams = new HashMap<>();
		otherParams.put("adminId", adminId);
		if (type == 1) {
			otherParams.put("delflag", DeleteFlag.VALID.getCode());
			return employeeMapper.getFullEmployeeByAdmin(otherParams);
		}
		return getObject(accountName, EXPIRED, TIMEUNIT, otherParams);
	}

	
	
	

}
