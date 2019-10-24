package test1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oo.businessplan.common.valid.ValidService;

public class TestService {
	
	 ApplicationContext context = null;
		
		@Before
		public void before() {
			context = new ClassPathXmlApplicationContext("spring-mvc.xml");
		}
		
		@After
		public void after () {
			
		}
		
		@Test
		public void testValid() {
			ValidService s = context.getBean("validService", ValidService.class);
			System.out.println(s == null);
		}
}
