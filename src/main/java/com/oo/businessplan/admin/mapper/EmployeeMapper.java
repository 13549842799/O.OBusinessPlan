package com.oo.businessplan.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.admin.pojo.entity.Employee;
import com.oo.businessplan.admin.pojo.form.EmployeeForm;
import com.oo.businessplan.basic.mapper.RedisCacheMapper;

public interface EmployeeMapper extends RedisCacheMapper<Employee>{


	 @Select("SELECT * FROM employee WHERE ecode=#{ecode,jdbcType=VARCHAR} AND stateId=IFNULL(#{state,jdbcType=TINYINT},stateId) AND delflag=#{delflag,jdbcType=TINYINT}")
	 Employee findEmployeeByCode(@Param("ecode")String ecode,@Param("state")Byte state,@Param("delflag")Byte delflag);
	 
	 @Update("UPDATE employee SET stateId=#{state,jdbcType=TINYINT} WHERE eid=#{eid,jdbcType=INTEGER} AND delflag=#{delete,jdbcType=TINYINT}")
	 int alterState(@Param("eid")int eid,@Param("state")byte state,@Param("delflag")byte delflag);
	 
	 @Update("UPDATE employee SET ecode=#{ecode} WHERE eid=#{eid}")
	 void updateEcode(@Param("ecode")String ecode,@Param("eid")int eid);
	 
	 Employee findEmployee(EmployeeForm eform);
	 
	 List<Employee> list(EmployeeForm form);

	 Employee testmethod(Map<String, Object> params);
}