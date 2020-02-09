package test1;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.target.mapper.PlanActionMapper;
import com.oo.businessplan.target.mapper.TargetPlanMapper;
import com.oo.businessplan.target.pojo.entity.Target;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.pojo.entity.TargetPlanAlterRecord;
import com.oo.businessplan.target.pojo.form.TargetPlanForm;

public class TargetTest extends BaseTest {

	@Test
	public void testTargetPlan() {
		TargetPlanMapper tpm = context.getBean("targetPlanMapper", TargetPlanMapper.class);
		
		//List<TargetPlan> count = tpm.overLappedTimePlans(2, DeleteFlag.VALID.getCode(), new Time(7, 50, 0), new Time(9, 50, 0), "1");
		/*List<TargetPlan> ps = tpm.unCompleteList(2, DeleteFlag.VALID.getCode());
		System.out.println(ps);*/
		/*TargetPlan p = new TargetPlan(DeleteFlag.VALID.getCode());
		p.setId(2);
		p = tpm.getById(p);
	    LocalDateTime t = LocalDateTime.of(LocalDate.now(), p.getExecutionTime().toLocalTime());

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	    String dateTime = t.format(formatter);*/
		System.out.println(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}
	
	@Test
	public void testRecord() {
		TargetPlanMapper tpm = context.getBean("targetPlanMapper", TargetPlanMapper.class);
		
        TargetPlanForm form  = new TargetPlanForm();
		
		Target target = new Target();
		target.setDelflag(DeleteFlag.VALID.getCode());
		target.setCreator(null);
		target.setState(Target.RUN);
		target.setExpectFinishTime(new Date());
		
		form.setTarget(target);
		form.setDelflag(DeleteFlag.VALID.getCode());
		List<TargetPlan> plans = tpm.getListByTarget(form);
		plans.forEach(p -> System.out.println(p));
	}
	
	@Test
	public void testTime() {
		Time time = new Time(8, 20, 60);
		
		System.out.println(time.before(new Date()));
	}
	
	@Test
	public void testArray() {
		PlanActionMapper pm = context.getBean("planActionMapper", PlanActionMapper.class);
		
		System.out.println(pm.getActionsStatic(2));
	}
}
