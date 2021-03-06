package test1;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.oo.businessplan.article.mapper.DiaryMapper;
import com.oo.businessplan.article.mapper.NovelMapper;
import com.oo.businessplan.article.mapper.SectionMapper;
import com.oo.businessplan.article.pojo.entity.Label;
import com.oo.businessplan.article.pojo.entity.Novel;
import com.oo.businessplan.article.pojo.form.DiaryForm;
import com.oo.businessplan.article.pojo.form.NovelForm;
import com.oo.businessplan.common.constant.ArticleConstant;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.target.mapper.PlanActionMapper;
import com.oo.businessplan.target.mapper.TargetPlanMapper;
import com.oo.businessplan.target.pojo.entity.PlanAction;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.upload.mapper.UploadFileMapper;
import com.oo.businessplan.upload.pojo.UploadFile;

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
	
	/**
	 * 最新章节的逻辑修改了，改为再novel中记录最新创建的章节的id，因为旧数据中没有记录这个字段的数据，所以要修复一下
	 */
	@Test
	public void fixLastetSection() {
		NovelMapper nm = context.getBean("novelMapper", NovelMapper.class);
		SectionMapper sm = context.getBean("sectionMapper", SectionMapper.class);
		Novel novel = new Novel();
		novel.setDelflag(DeleteFlag.VALID.getCode());
		List<Novel> list = nm.getList(novel);
		for (Novel n : list) {
			Long sid = sm.getlastetId(n.getId());
			if (sid == null) {
				continue;
			}
			Novel temp = new Novel(n.getId());
			temp.setLastetSectionId(sid);
			nm.update(temp);
		}
	}
	
	/**
	 * 因为封面的逻辑修改了，所以要修复数据
	 */
	@Test
	public void fixCover() {
		NovelMapper nm = context.getBean("novelMapper", NovelMapper.class);
		UploadFileMapper um = context.getBean("uploadFileMapper", UploadFileMapper.class);
		
		Novel novel = new Novel();
		novel.setDelflag(DeleteFlag.VALID.getCode());
		List<Novel> list = nm.getList(novel);
		for (Novel n : list) {
			if (StringUtil.isEmpty(n.getCover())) {
				continue;
			}
			UploadFile file = new UploadFile(n.getCover(), UploadFile.NOVEL, 1l);
			file.setCreateTime(new Timestamp(new Date().getTime()));
			file.setCreator(n.getCreator());
			file.setDelflag(DeleteFlag.VALID.getCode());
			file.setTheType();
			file.setName("covertImage");
			file.setObjId((long)n.getId());
			try {
				um.add(file);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testTiime() {
		Date date = new Date();
		System.out.println(SimpleDateFormat.getTimeInstance(2).format(date));
	}
	
	@Test
	@Transactional
	public void fixTime() {
		TargetPlanMapper tpm = context.getBean("targetPlanMapper", TargetPlanMapper.class);
		PlanActionMapper pam = context.getBean("planActionMapper", PlanActionMapper.class);
		TargetPlan p = new TargetPlan(DeleteFlag.VALID.getCode());
		List<TargetPlan> plans = tpm.getList(p);
		if (plans == null) {
			System.out.println("不存在");
			return;
		}
		try {
		for (TargetPlan targetPlan : plans) {
			String st = SimpleDateFormat.getTimeInstance(2).format(targetPlan.getExecutionTime()),
				et =  SimpleDateFormat.getTimeInstance(2).format(targetPlan.getEndTime());
			
	        PlanAction param = new PlanAction();
	        param.setTargetPlanId(targetPlan.getId());
			List<PlanAction> pa = pam.getList(param);
			for (PlanAction planAction : pa) {
				Date curDate = planAction.getActionDate();
				String cstr = SimpleDateFormat.getDateInstance().format(curDate);
				Date newS = SimpleDateFormat.getDateTimeInstance().parse(cstr + " " + st);
				Date newe = SimpleDateFormat.getDateTimeInstance().parse(cstr + " " + et);					
				planAction.setExpectStartTime(new Timestamp(newS.getTime()));
				planAction.setExpectEndTime(new Timestamp(newe.getTime()));
				pam.update(planAction);
			}
		}
		} catch (ParseException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
}
