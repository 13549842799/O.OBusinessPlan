package test1;

import java.sql.Time;
import java.util.List;

import org.junit.Test;

import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.target.mapper.TargetPlanMapper;
import com.oo.businessplan.target.pojo.entity.TargetPlan;

public class TargetTest extends BaseTest {

	@Test
	public void testTargetPlan() {
		TargetPlanMapper tpm = context.getBean("targetPlanMapper", TargetPlanMapper.class);
		
		List<TargetPlan> count = tpm.overLappedTimePlans(2, DeleteFlag.VALID.getCode(), new Time(7, 50, 0), new Time(9, 50, 0), "1");
		System.out.println(count);
	}
}
