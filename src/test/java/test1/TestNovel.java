package test1;

import org.junit.Test;

import com.oo.businessplan.article.mapper.NovelMapper;
import com.oo.businessplan.article.pojo.entity.Novel;
import com.oo.businessplan.article.pojo.form.NovelForm;
import com.oo.businessplan.common.enumeration.DeleteFlag;

public class TestNovel extends BaseTest {

	@Test
	public void testExpandNovel () {
		NovelMapper nm = context.getBean("novelMapper", NovelMapper.class);
		Novel novel = new Novel();
		novel.setId(10);
		novel.setCreator(2);
		novel.setDelflag(DeleteFlag.VALID.getCode());
		NovelForm nf = nm.getComplete(novel);
		System.out.println(nf.getLastetSection().getCreateTimeStr());
	}
}
