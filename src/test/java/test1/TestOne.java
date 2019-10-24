package test1;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.log.Log;
import com.oo.businessplan.admin.mapper.AcceptMessageMapper;
import com.oo.businessplan.admin.mapper.AdminMapper;
import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.authority.mapper.AuthorityMapper;
import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.AuthorityWithKey;
import com.oo.businessplan.authority.pojo.Role;
import com.oo.businessplan.common.email.EmailServer;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.enumeration.StatusFlag;
import com.oo.businessplan.common.redis.RedisTokenManager;
import com.oo.businessplan.common.util.PassUtil;
import com.oo.businessplan.common.util.XmlUtil;

import io.swagger.models.auth.In;

public class TestOne {
	
	private AbstractApplicationContext ctx;
	
	@Before
	public void before(){
		 ctx = new ClassPathXmlApplicationContext("spring-redis.xml","spring-dao.xml","spring-email.xml");
	}
	
	@Test
	public void testEmail() throws Exception{
		EmailServer server = ctx.getBean("emailServer",EmailServer.class);
		StringBuffer sb = new StringBuffer();
        Map<String,String> params = new HashMap<>();
        params.put("content", "CAIYANGZHIISGOOD");
        File[] files = new File[2];
        files[0]=new File("e:"+File.separator+"素材"+File.separator+"自制扑克"+File.separator+"大王.png");
        files[1]=new File("e:"+File.separator+"素材"+File.separator+"自制扑克"+File.separator+"方块4.png");
        server.sendEmail("caiyangzhi123@163.com", "今晚测试", files,params );
	    Thread.sleep(5000);
	}
	
	@Test
	public void testEmailTemplate() throws Exception{
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d.]*$");  
        boolean re = pattern.matcher("15").matches();
        System.out.println(re);
	}
	
	@Test
	public void testRedis(){
		
		RedisTokenManager manager = ctx.getBean("tokenManager",RedisTokenManager.class);
		
		HashOperations<String,String,Object> hop = manager.getRedisTemplate().opsForHash();
		 
		Admin admin = new Admin();
		admin.setId(1);
		admin.setAccountname("测试");
		admin.setDelflag(DeleteFlag.VALID.getCode());
		admin.setNikename("昵称");
		admin.setPassword("adafadsfkadsjfkaldfkads");
		Map<String,Admin> map = new HashMap<>();
		map.put("admin", admin);
		//hop.put("oo", "admin", admin);
		//manager.getRedisTemplate().expire("oo",manager.getExpireds()[1],manager.getTimeUnits()[1]);
		for (int i = 1; i <1; i++) {
			try {
			Thread.sleep(1000);
			System.out.println("以等待了"+i+"秒");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		long time = manager.getRedisTemplate().getExpire("ff");
		System.out.println(time);
		Object o = hop.get("oo", "admin");
		System.out.println(o);
		
	}
	
	
	@Test
	public void testjson(){
		 /*Role role = new Role(1,"11111111",2,"1","1",1);
		 Map map = new HashMap<>();
		 map.put("id",role.getId());
		 map.put("code",role.getCode());
		 map.put("name",role.getName());
		 map.put("type",role.getType());
		 map.put("describe", role.getDescribe());
		 map.put("status", role.getStatus());
		 
		 long start = System.currentTimeMillis();
		 String string = JSONObject.toJSONString(role);
		 long end = System.currentTimeMillis();
		 System.out.println(end-start);
		 System.out.println(string);*/
		byte a =1;
		System.out.println(a);
		
	}
	
	@Test
	public void testPassword(){
		try {
			String paString = PassUtil.getEncryptedPwd("OB1000");
			System.out.println(paString);
			System.out.println(PassUtil.validPassword("OB1000", paString));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAdmin(){
		AdminMapper adminMapper =  ctx.getBean("adminMapper",AdminMapper.class);
		Admin admin = new Admin();
		//admin = adminMapper.find(admin);
		//adminMapper.getAdmin(0, null,DeleteFlag.VALID.getCode(), DeleteFlag.VALID.getCode());
		//adminMapper.findAdmin(admin);
		//adminMapper.find(admin);
		//System.out.println(admin);
		//admin = adminMapper.getByStr("OB1000", DeleteFlag.VALID.getCode(), StatusFlag.ENABLE.getCode());
		admin.setAccountname("OB1001");
		admin.setNikename("adf");
		admin.setPassword("adf");
		admin.setRelatedid(2);
		try {
			adminMapper.add(admin);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//int i = adminMapper.stateAdmin(1,StatusFlag.ENABLE.getCode(), StatusFlag.ENABLE.getCode());
		System.out.println(admin);
	}
	
	@Test
	public void testsudu(){
		List<String> list = new ArrayList<>();
		
		Set<String> set = new HashSet<>();;

		for (int i = 0; i <1000; i++) {
			 set.add(String.valueOf(i));
			 list.add(String.valueOf(i));
		}
		
		long start = System.currentTimeMillis();
		for (int i = 0; i <500; i++) {
			if (list.get(i).equals("499")) {
				System.out.println("ok");
			}
		}
		long end = System.currentTimeMillis()-start;
		System.out.println(end);
		
	}
	
	@Test
	public void testMessage(){
		AcceptMessageMapper aMapper =   ctx.getBean("acceptMessageMapper",AcceptMessageMapper.class);
	    List<Map<String,Object>> list = aMapper.getAdminMessage(1,(byte)1,(byte) 1,(byte) 1);
	}
	
	@Test
	public void testAuthorityWithKey () {
		AuthorityMapper authMapper = ctx.getBean("authorityMapper", AuthorityMapper.class);
		System.out.println(authMapper == null);
		List<Authority> list = authMapper.getListByStr("OB1000", 
				DeleteFlag.VALID.getCode(), StatusFlag.ENABLE.getCode());
		System.out.println(list.size());
		Authority a = list.get(0);
		System.out.print((AuthorityWithKey)a);
	}
}
