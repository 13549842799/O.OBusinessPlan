package test1;

import java.util.List;

import org.junit.Test;

import com.oo.businessplan.admin.mapper.EmployeeMapper;
import com.oo.businessplan.admin.pojo.entity.Employee;

public class EmployeeTest extends BaseTest {

	public EmployeeMapper getMapper () {
		return context.getBean("employeeMapper", EmployeeMapper.class);
	}
	
	@Test
	public void testList () {
		List<Employee> list = getMapper().getList(new Employee());
	}
}
