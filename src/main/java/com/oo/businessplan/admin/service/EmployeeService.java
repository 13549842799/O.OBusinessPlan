package com.oo.businessplan.admin.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.oo.businessplan.admin.pojo.entity.Employee;
import com.oo.businessplan.basic.service.BaseService;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.exception.PatternErrorException;

public interface EmployeeService extends BaseService<Employee>{
	
	 /**
	  * 增加职员
	  * @param employee
	 * @throws PatternErrorException 
	  */
	 public void  addEmployee(Employee employee) throws PatternErrorException;
	 
	 /**
	  * 上传员工的证件照片
	  * @param request
	  * @param modifier
	  * @return
	 * @throws ObjectNotExistException 
	  */
	 public void upLoadAvatar(HttpServletRequest request,int employeeId,Integer modifier) throws ObjectNotExistException;

}
