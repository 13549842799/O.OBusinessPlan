package test1;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.oo.businessplan.article.mapper.ClassifyMapper;
import com.oo.businessplan.article.pojo.entity.Classify;
import com.oo.businessplan.common.enumeration.DeleteFlag;

public class ArticleTest extends BaseTest {
	
	@Test
	public void addClass() {
		ClassifyMapper cm = context.getBean("classifyMapper", ClassifyMapper.class);
		
		Classify cls = null;
		for (int i = 0; i < 50; i++) {
			cls = new Classify(DeleteFlag.VALID.getCode());
			cls.setChildType(Classify.DIARY);
			cls.setType(Classify.CUSTOMCLASSIFY);
			cls.setCreator(2);
			cls.setName("新的" + i);
			try {
				cm.add(cls);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
