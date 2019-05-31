package com.oo.businessplan.admin.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.admin.pojo.entity.Employee;
import com.oo.businessplan.admin.service.EmployeeService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.constant.SystemKey;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.exception.PatternErrorException;
import com.oo.businessplan.common.net.SessionInfo;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.UpLoadUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/employee")
@Api("职员模块")
public class EmployeeController extends BaseController{
	
	
	    @Autowired
	    private EmployeeService employeeService;
	
	    @RequestMapping(value="/page/add/addEmployee.do",method=RequestMethod.POST)
	    @ApiOperation(value = "员工信息入档")
	    @IgnoreSecurity(val=true)
	    public ResponseResult<String> employeeSave(HttpServletRequest request,
				   @ApiParam(value = "姓名", required = false)  @RequestParam(required=false,value="name")String name,
				   @ApiParam(value = "性别", required = false)  @RequestParam(required=false,value="sex")String sex,
				   @ApiParam(value = "出生日期", required = false)  @RequestParam(required=false,value="birth")Long birth,
				   @ApiParam(value = "个人身份证号码", required = false)  @RequestParam(required=false,value="idCard")String idCard,
				   @ApiParam(value = "婚姻状况", required = false)  @RequestParam(required=false,value="marriageId")Byte marriageId,
				   @ApiParam(value = "民族", required = false)  @RequestParam(required=false,value="nationId")Integer nationId,
				   @ApiParam(value = "电话", required = false)  @RequestParam(required=false,value="phone")String phone,
				   @ApiParam(value = "邮箱", required = false)  @RequestParam(required=false,value="email")String email,
				   @ApiParam(value = "QQ", required = false)  @RequestParam(required=false,value="qq")String qq,
				   @ApiParam(value = "家庭住址", required = false)  @RequestParam(required=false,value="address")String address,
				   @ApiParam(value = "学历", required = false)  @RequestParam(required=false,value="qualificationId")Integer qualificationId,
				   @ApiParam(value = "毕业院校", required = false)  @RequestParam(required=false,value="school")String school,
				   @ApiParam(value = "职位", required = false)  @RequestParam(required=false,value="positionId")Integer positionId,
				   @ApiParam(value = "部门", required = false)  @RequestParam(required=false,value="departmentId")Integer departmentId,
				   @ApiParam(value = "薪水", required = false)  @RequestParam(required=false,value="salary")BigDecimal salary,
				   @ApiParam(value = "薪水类型", required = false)  @RequestParam(required=false,value="salaryTypeId")Integer salaryTypeId,
				   @ApiParam(value = "入职时间", required = false)  @RequestParam(required=false,value="beginTime")Long beginTime,
				   @ApiParam(value = "员工状态", required = false)  @RequestParam(required=false,value="stateId")Byte stateId){
	    	
	    	  ResponseResult<String> response = new ResponseResult<>();
	    	  
	    	  Timestamp birth_t = birth==null?null:new Timestamp(birth);
	    	  Timestamp beginTime_t = beginTime==null?null:new Timestamp(beginTime);
	    	  
	    	  Employee employee = new Employee(null, null, name, sex, birth_t, idCard, marriageId, nationId, null, phone, email, qq, address
	    			  , qualificationId, null, school, null, positionId, null, departmentId, null, stateId
	    			  , null, salary, salaryTypeId, null, beginTime_t
	    			  , null, null, null, null, DeleteFlag.VALID.getCode(), null);
	    	  
	    	  
	    	  try {
				SessionInfo info = matchSessionInfo(request);
				employee.setCreator((int)info.getId());
				employeeService.addEmployee(employee);		
				return response.success("添加成功");
			  } catch (Exception e) {
				e.printStackTrace();
				return response.fail(e.getMessage());
			  } 
	    	   	
	    }
	    
	    @GetMapping("/info.re")
	    @ApiOperation(value = "通过账号获取关联的员工信息")
	    // @IgnoreSecurity()
	    public ResponseResult<Employee> employeeInfo(HttpServletRequest request){
	    	
	    	  ResponseResult<Employee> response = new ResponseResult<>();
	    	  
	    	  SessionInfo info = matchSessionInfo(request);
	    	  Employee employee = employeeService.getByAdmin(Integer.parseInt(String.valueOf(info.getId())), info.getName());   	  	  
	    	  return response.success(employee); 	
	    }
	    
	    @GetMapping("/temp.re")
	    @ApiOperation(value = "通过账号获取关联的员工信息")
	    // @IgnoreSecurity()
	    public ResponseResult<Employee> employeeEdit(HttpServletRequest request,
	    		@RequestBody Employee employee){
	    	
	    	  ResponseResult<Employee> response = new ResponseResult<>();
	    	  
	    	  SessionInfo info = matchSessionInfo(request);
	    	  int adminId = Integer.parseInt(String.valueOf(info.getId()));
	    	  
	    	  return response.success(employee); 	
	    }
	    
	    @RequestMapping(value="/page/add/uploadAvatar.do",method=RequestMethod.POST)
	    @ApiOperation(value = "上传员工照片")
	    @IgnoreSecurity(val=true)
	    public ResponseResult<String> uploadAvatar(HttpServletRequest request,
	    		@ApiParam(value = "员工id", required = false)  @RequestParam(required=false,value="employeeId")int employeeId){
	    	
	    	  ResponseResult<String> response = new ResponseResult<>();
	    	  
	    	  SessionInfo info;
			  try {
				  info = matchSessionInfo(request);
				  employeeService.upLoadAvatar(request,employeeId,(int)info.getId());
		    	  
			  } catch (ObjectNotExistException e) {
				  e.printStackTrace();
				  response.fail("已过期");
			  }    	  	  
	    	  return response.success("上传成功"); 	
	    }
	     

}
