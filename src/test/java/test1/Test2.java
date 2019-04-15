package test1;

import org.junit.Test;

import com.oo.businessplan.article.pojo.entity.Classify;

public class Test2 {

	
	@Test
	public void testType(){
		Classify cls = new Classify(10);
        System.out.println(cls.getId());
	}
}
