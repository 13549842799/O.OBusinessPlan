package test1;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oo.businessplan.admin.mapper.AdminMapper;
import com.oo.businessplan.admin.pojo.form.AdminForm;
import com.oo.businessplan.admin.pojo.page.Padmin;
import com.oo.businessplan.authority.mapper.AuthorityMapper;
import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.enumeration.StatusFlag;

public class MyBatisTest {
	
	ApplicationContext context = null;
	
	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("spring-dao.xml");
	}
	
	@After
	public void after () {
		
	}
	
	@Test
	public void test() {
		AdminMapper  am = context.getBean("adminMapper", AdminMapper.class);
		AdminForm adminForm = new AdminForm();
	    adminForm.setState(StatusFlag.ENABLE.getCode());
	    adminForm.setDelflag(DeleteFlag.VALID.getCode());
	    List<Padmin> ads = am.getListByForm(adminForm);
	    ads.forEach(System.out::println);
	}
	
	

}
