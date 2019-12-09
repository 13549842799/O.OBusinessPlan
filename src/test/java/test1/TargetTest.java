package test1;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.target.mapper.TargetPlanMapper;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.pojo.entity.TargetPlanAlterRecord;

public class TargetTest extends BaseTest {

	@Test
	public void testTargetPlan() {
		TargetPlanMapper tpm = context.getBean("targetPlanMapper", TargetPlanMapper.class);
		
		//List<TargetPlan> count = tpm.overLappedTimePlans(2, DeleteFlag.VALID.getCode(), new Time(7, 50, 0), new Time(9, 50, 0), "1");
		List<TargetPlan> ps = tpm.unCompleteList(2, DeleteFlag.VALID.getCode());
		System.out.println(ps);
	}
	
	@Test
	public void testRecord() {
		TargetPlanMapper tpm = context.getBean("targetPlanMapper", TargetPlanMapper.class);
		
		List<TargetPlanAlterRecord> records = tpm.getRecordsList(1, 2);
		records.forEach(o->System.out.println(o));
	}
	
	@Test
	public void testTime() {
		Time time = new Time(8, 20, 60);
		
		System.out.println(time.before(new Date()));
	}
}
