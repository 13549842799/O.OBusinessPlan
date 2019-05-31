package test1;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.admin.mapper.AdminMapper;
import com.oo.businessplan.admin.mapper.EmployeeMapper;
import com.oo.businessplan.admin.pojo.form.AdminForm;
import com.oo.businessplan.admin.pojo.page.Padmin;
import com.oo.businessplan.article.mapper.ClassifyMapper;
import com.oo.businessplan.article.mapper.DiaryMapper;
import com.oo.businessplan.article.pojo.entity.Classify;
import com.oo.businessplan.article.pojo.entity.Diary;
import com.oo.businessplan.article.pojo.form.DiaryForm;
import com.oo.businessplan.article.service.ClassifyService;
import com.oo.businessplan.authority.mapper.AuthorityMapper;
import com.oo.businessplan.authority.mapper.ResourceMapper;
import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.Resource;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.enumeration.StatusFlag;
import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import io.swagger.util.Json;

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
	
	@Test
	public void testDiary () 
	{
		DiaryMapper dm = context.getBean("diaryMapper", DiaryMapper.class);
		DiaryForm form = new DiaryForm();
		form.setCreator(1);
		form.setPageNum(1);
		form.setPageSize(4);
		PageHelper.startPage(form.getPageNum(), form.getPageSize());
		List<Diary> di = dm.getList(form);
		PageInfo<Diary> page = new PageInfo<>(di);
		System.out.println(page);
	}
	
	@Test
	public void testEmployee () {
		EmployeeMapper mapper = context.getBean("employeeMapper", EmployeeMapper.class);
		Map<String, Object> params = new HashMap<>();
		params.put("ecode", "12312");
		params.put("ab", "12312");
		mapper.testmethod(params);
	}

}
