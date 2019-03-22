package com.oo.businessplan.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oo.businessplan.admin.mapper.EmployeeMapper;
import com.oo.businessplan.admin.pojo.entity.Employee;
import com.oo.businessplan.admin.service.EmployeeService;
import com.oo.businessplan.basic.mapper.BaseMapper;
import com.oo.businessplan.basic.service.RedisCacheService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.constant.EntityConstants;
import com.oo.businessplan.common.constant.ResultConstant;
import com.oo.businessplan.common.constant.SystemKey;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.exception.PatternErrorException;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.common.util.UpLoadUtil;

@Service("employeeService")
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService, RedisCacheService<Employee> {
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Autowired
	private UpLoadUtil upLoadUtil;
	
	@Override
	public Employee getObject(String key, int expired, int timeUnit) {
		return super.getObject((BaseMapper<Employee>) employeeMapper, key,EntityConstants.REDIS_EMPLOYEE_NAME,expired, timeUnit);
	}

	@Override
	public List<Employee> getListObject(String key, int expired, int timeUnit) {
		return super.getListObject((BaseMapper<Employee>) employeeMapper, key, EntityConstants.REDIS_EMPLOYEE_NAME, expired, timeUnit);
	}

	@Override
	@Transactional
	public void addEmployee(Employee employee) throws PatternErrorException {
		
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
		employeeMapper.insert(employee);//此处存在触发器，部门表人数字段自动增1
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
		
		Map<String,String> result = new HashMap<>();
		
		Map<String,Map<String,String>> params = new HashMap<>();
  	    Map<String,String> config = new HashMap<>();
  	    config.put("type","img");
  	    config.put("newName", null);
  	    config.put("targetPath","");
  	    params.put("avatar", config);	    	  
  	    Map<String,String> paths = upLoadUtil.uploadFile(request, params);
  	    String newPath = paths.get("avatar");
  	    if (newPath==null) {
		    throw new ObjectNotExistException(ResultConstant.AVATAR_UPLOAD_ERROR);
	    }
  	    Employee employee = new Employee();
  	    employee.setEid(employeeId);
  	    employee.setAvatar(newPath);
  	    employee.setDelflag(DeleteFlag.VALID.getCode());
  	    employeeMapper.update(employee);
		
	}
	
	
	

}
