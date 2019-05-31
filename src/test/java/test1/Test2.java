package test1;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import com.oo.businessplan.article.pojo.entity.Classify;

public class Test2 {

	
	@Test
	public void testType(){
		long t = System.currentTimeMillis();
		Timestamp tt = new Timestamp(t);
		System.out.println(tt);
	}
}
