package com.oo.businessplan.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.admin.pojo.entity.Department;
import com.oo.businessplan.admin.pojo.entity.Employee;


public interface DepartmentMapper {
	
	
	@Select("SELECT * FROM department WHERE did=#{did,jdbcType=INTEGER} AND state=IFNULL(#{state,jdbcType=TINYINT},state) AND delflag=#{delflag,jdbcType=TINYINT}")
 	Department findDepartmentById(@Param("did")int did,@Param("state")Byte state,@Param("delfalg")Byte delflag );

	@Select("SELECT * FROM department WHERE dcode=#{dcode,jdbcType=VARCHAR} AND state=IFNULL(#{state,jdbcType=TINYINT},state) AND delflag=#{delflag,jdbcType=TINYINT}")
	Department findDepartmentByCode(@Param("dcode")String dcode,@Param("state")Byte state,@Param("delfalg")Byte delflag);

	@Update("UPDATE department SET delflag=#{delflag,jdbcType=TINYINT} WHERE did=#{did,jdbcType=INTEGER} AND delflag=#{delete,jdbcType=TINYINT}")
	int delete(@Param("did")int did,@Param("delflag")byte delflag,@Param("delete")byte delete);
	 
	@Update("UPDATE department SET state=#{state,jdbcType=TINYINT} WHERE did=#{did,jdbcType=INTEGER} AND delflag=#{delete,jdbcType=TINYINT}")
	int alterState(@Param("did")int did,@Param("state")byte state,@Param("delflag")byte delflag);
	
	Department findDepartment(Department department);
	
	List<Department> list();
	
	int update(Department department);
	
	void insert(Department department);

}
