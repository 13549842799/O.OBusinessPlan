package com.oo.businessplan.target.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.support.RedisCacheSupport;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.target.mapper.TargetPlanMapper;
import com.oo.businessplan.target.service.TargetPlanService;
import com.oo.businessplan.target.pojo.entity.Target;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.pojo.form.TargetPlanForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-10-24 16:38:41
 */
@Service("targetPlanService")
public class TargetPlanServiceImpl extends RedisCacheSupport<TargetPlan> implements TargetPlanService {

    @Autowired
    TargetPlanMapper targetPlanMapper;

    /**
     * 获取当天的当前时间后将要或正在进行（结束时间大于当前时间）的目标（运行状态）计划。
     * 然后通过时间的单位和周期计算筛选出当天需要进行的计划。
     */
	@Override
	public List<TargetPlan> getWillExecutePlanListInDay(int creator) {
		
		List<TargetPlan> willPlans = new LinkedList<>();
		
		TargetPlanForm form  = new TargetPlanForm();
		
		Target target = new Target();
		target.setDelflag(DeleteFlag.VALID.getCode());
		target.setCreator(creator);
		target.setState(Target.RUN);
		
		form.setTarget(target);
		form.setDelflag(DeleteFlag.VALID.getCode());

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		List<TargetPlan> plans = targetPlanMapper.getListByTarget(form);
		for (TargetPlan plan : plans) {

		    LocalDate startDate = LocalDate.parse(sdf.format(plan.getStartDate()));//开始时间
		    LocalDate now = LocalDate.now();
		    
		    if (!this.isCome(startDate, now, plan.getPeriod(), plan.getUnit())) {
		    	continue;
		    }		   
		    willPlans.add(plan);
		}
		
		return willPlans;
	}
    
	/**
	 * 
	 * @param startDate
	 * @param now
	 * @param period
	 * @param unit
	 * @return
	 */
	private boolean isCome(LocalDate startDate, LocalDate now, int period, byte unit) {
		if (startDate.isAfter(now)) {
	    	return false;
	    }
		Long days = null, res = null;
		switch (unit) {
			case 2: //天
				days = ChronoUnit.DAYS.between(startDate, now);
				res = days%period;
				return res == 0;
			case 3://周
				days = ChronoUnit.DAYS.between(startDate, now);
				res = days%(period*7);
				return res == 0;
			case 4://月
				long dis = ChronoUnit.MONTHS.between(startDate, now);
				dis = startDate.getDayOfMonth() > now.getDayOfMonth() ? dis + 1 : dis;			    
				return dis%period == 0 && startDate.getDayOfMonth() == now.getDayOfMonth();
			default:
				return false;
	    }
	}
   
}