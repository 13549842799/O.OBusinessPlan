package test1;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
		AuthorityMapper  authMapper = context.getBean("authorityMapper", AuthorityMapper.class);
		List<Authority> auths = authMapper.getAuthoritiesByAdminIdAndType(1, Authority.AWARD, DeleteFlag.VALID.getCode(), StatusFlag.ENABLE.getCode());
	    Map<Integer, List<Authority>> authMap = auths.stream().collect(Collectors.groupingBy(Authority::getReid));
	    System.out.println(authMap);
	}

}
