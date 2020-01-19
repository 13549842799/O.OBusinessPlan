package test1;

import java.util.List;

import org.junit.Test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.article.mapper.NovelMapper;
import com.oo.businessplan.article.pojo.form.NovelForm;
import com.oo.businessplan.common.enumeration.DeleteFlag;

public class TestNovel2 extends BaseTest {

	@Test
	public void testNovel() {
		NovelMapper nMapper = context.getBean("novelMapper", NovelMapper.class);
		
		NovelForm form = new NovelForm();
		form.setCreator(2);
		form.setDelflag(DeleteFlag.VALID.getCode());
		form.setPageNum(1);
		form.setPageSize(10);
		PageHelper.startPage(form.getPageNum(), form.getPageSize());
		List<NovelForm> list = nMapper.getExpandList(form);
		PageInfo<NovelForm> page = new PageInfo<>(list);
	}
}
