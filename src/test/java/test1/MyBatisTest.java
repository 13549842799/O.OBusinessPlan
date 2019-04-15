package test1;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.admin.mapper.AdminMapper;
import com.oo.businessplan.admin.pojo.form.AdminForm;
import com.oo.businessplan.admin.pojo.page.Padmin;
import com.oo.businessplan.article.mapper.ClassifyMapper;
import com.oo.businessplan.article.pojo.entity.Classify;
import com.oo.businessplan.article.service.ClassifyService;
import com.oo.businessplan.authority.mapper.AuthorityMapper;
import com.oo.businessplan.authority.mapper.ResourceMapper;
import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.Resource;
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
	
	@Test
	@Transactional
	public void testInsert() {
		ResourceMapper rm   = context.getBean("resourceMapper", ResourceMapper.class);
		
		try {
			Resource r = new Resource(null, null, "sadf", "asdf", "sadf", null, (byte)1, null, "asdf", StatusFlag.ENABLE.getCode(), DeleteFlag.VALID.getCode());
			rm.add(r);
			Integer i = r.getIdAsInt();
			System.out.println(i);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
	   
	}
	
	@Test
	public void testClassify() {
		ClassifyMapper cm   = context.getBean("classifyMapper", ClassifyMapper.class);
		Classify classify  = new Classify();
		classify.setChildType((byte)1);
		classify.setCreator(1);
		classify.setDelflag(DeleteFlag.VALID.getCode());
		try {
			PageHelper.startPage(1,15);
			List<Classify> list = cm.getList(classify);
			PageInfo<Classify> page = new PageInfo<>(list);
			System.out.println(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}
	
	@Test
	public void testId () throws  NoSuchMethodException {
		ClassifyMapper cm   = context.getBean("classifyMapper", ClassifyMapper.class);
		Classify classify  = new Classify();
		classify.setId(1);
		classify.setDelflag(DeleteFlag.VALID.getCode());
		classify = cm.getById(classify);
		System.out.println(cm.checkTheClassifyArticleCount(classify.getThisTarget(), 1, classify.getCreator(), DeleteFlag.VALID.getCode()));
	}
	

}
