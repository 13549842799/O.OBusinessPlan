package test1;

import java.util.List;

import org.junit.Test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
	
	@Test
	public void testExpandNovelList () {
		NovelMapper nm = context.getBean("novelMapper", NovelMapper.class);
		System.out.println("开始测试");
		NovelForm form = new NovelForm();
		form.setCreator(2);
		form.setDelflag(DeleteFlag.VALID.getCode());
        form.setPageNum(1);
        form.setPageSize(10);
		PageHelper.startPage(form.getPageNum(), form.getPageSize());
		List<NovelForm> list = nm.getExpandList(form);	
		PageInfo<NovelForm> page = new PageInfo<>(list);
	}
	
	@Test
	public void testNovelOne () {
		NovelMapper nm = context.getBean("novelMapper", NovelMapper.class);
		Novel novel = new Novel();
		novel.setId(18);
		novel.setCreator(2);
		novel.setDelflag(DeleteFlag.VALID.getCode());
		NovelForm nf = nm.getComplete(novel);

	}
}
