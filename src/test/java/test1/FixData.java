package test1;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.oo.businessplan.article.mapper.DiaryMapper;
import com.oo.businessplan.article.mapper.NovelMapper;
import com.oo.businessplan.article.pojo.entity.Label;
import com.oo.businessplan.article.pojo.form.DiaryForm;
import com.oo.businessplan.article.pojo.form.NovelForm;
import com.oo.businessplan.common.constant.ArticleConstant;
import com.oo.businessplan.common.enumeration.DeleteFlag;

public class FixData extends BaseTest {

	@Test
	public void test() {
		
		//DiaryMapper dm = context.getBean("diaryMapper", DiaryMapper.class);
		NovelMapper nm = context.getBean("novelMapper", NovelMapper.class);
		
		NovelForm f = new NovelForm();
		f.setDelflag(DeleteFlag.VALID.getCode());
		List<NovelForm> forms = nm.getExpandList(f);
		List<Label> ls = new ArrayList<Label>();
		Set<Integer> ids = new HashSet<>();
		if (forms != null) {
			Timestamp now = new Timestamp(new Date().getTime());
			StringBuilder sb = new StringBuilder();
			for (NovelForm diaryForm : forms) {
				if (ids.contains(diaryForm.getId())) {
					continue;
				}
				List<Label> dls = diaryForm.getLabelList();
				if (dls == null || dls.size() == 0) {
					continue;
				}
				for (Label label : dls) {
					label.setDelflag(DeleteFlag.VALID.getCode());
					label.setCreateTime(now);
					label.setCreator(diaryForm.getCreator());
					label.setTargetId(diaryForm.getId());
					label.setTargetType(ArticleConstant.ArticleType.NOVEL);
					ls.add(label);
				}
				
			}
			for (Label label : ls) {
				sb.append("INSERT INTO label ( delflag,creator,createTime,`name`,targetType,targetId) ")
				.append("VALUES (").append(label.getDelflag()).append(",").append(label.getCreator()).append(",")
				.append(label.getCreateTime()).append(",'").append(label.getName()).append("',").append(label.getTargetType()).append(",")
				.append(label.getTargetId()).append(");\n\r");
			}
			System.out.println(sb.toString());
		}
	}
}
