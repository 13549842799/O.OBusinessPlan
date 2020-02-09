package com.oo.businessplan.target.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.support.RedisCacheSupport;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.AddErrorException;
import com.oo.businessplan.target.mapper.PlanActionMapper;
import com.oo.businessplan.target.mapper.TargetPlanMapper;
import com.oo.businessplan.target.service.TargetPlanService;
import com.oo.businessplan.target.pojo.entity.PlanAction;
import com.oo.businessplan.target.pojo.entity.Target;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.pojo.entity.TargetPlanAlterRecord;
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
    
    @Autowired
    PlanActionMapper actionMapper;

	/**
     * 获取当天的当前时间后将要或正在进行（结束时间大于当前时间）的目标（运行状态）计划。
     * 然后通过时间的单位和周期计算筛选出当天需要进行的计划。
     */
	@Override
	public List<TargetPlan> getWillExecutePlanListInDay(Integer creator) {
		
		List<TargetPlan> willPlans = new LinkedList<>();
		
		TargetPlanForm form  = new TargetPlanForm();
		
		Target target = new Target();
		target.setDelflag(DeleteFlag.VALID.getCode());
		target.setCreator(creator);
		target.setState(Target.RUN);
		target.setExpectFinishTime(new Date());
		
		form.setTarget(target);
		form.setEndDate(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		form.setDelflag(DeleteFlag.VALID.getCode());
		List<TargetPlan> plans = targetPlanMapper.getListByTarget(form);
		for (TargetPlan plan : plans) {
		    LocalDate startDate = plan.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//开始时间
		    LocalDate now = LocalDate.now();
		    
		    if (!this.isCome(startDate, now, plan.getPeriod(), plan.getUnit())) {
		    	continue;
		    }		   
		    willPlans.add(plan);
		}
		
		if (willPlans.size() == 0 || creator == null) { //如果creator == null，则表示调用此方法的是定时任务，无需要判断action
		    return willPlans;
		}
		//为将要执行的计划设置动作，如果这个动作已经完成了或放弃了，则查找下一个
		Iterator<TargetPlan> it = willPlans.iterator();
		while (it.hasNext()) {
			TargetPlan plan = it.next();
			PlanAction action = actionMapper.getLaststActionToday(plan.getId());
			
			if (action == null) {
				long dayMinSecond = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli();
	    		Date now = new Date(dayMinSecond);
				action = new PlanAction();
    			action.setTargetPlanId(plan.getId());
    			action.setResult(PlanAction.UNSTART);
    			action.setActionDate(now); 
    			action.setExpectStartTime(new Timestamp(LocalDateTime.of(LocalDate.now(), plan.getExecutionTime().toLocalTime()).toInstant(ZoneOffset.of("+8")).toEpochMilli()));
    			action.setExpectEndTime(new Timestamp(LocalDateTime.of(LocalDate.now(), plan.getEndTime().toLocalTime()).toInstant(ZoneOffset.of("+8")).toEpochMilli()));
    			action.setNum(plan.countAddOne());
				try {
					actionMapper.add(action);
					List<TargetPlan> ps = new LinkedList<>();
					ps.add(plan);
					targetPlanMapper.updateCountBatch(ps);
				} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}
			
			plan.setAction(action);
			if (action.getResult() > 2) {
				it.remove();
				continue;
			}
			break;
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

	@Override
	public List<TargetPlan> isOverLappedTime(TargetPlan plan) {
		
		List<TargetPlan> lapperds = targetPlanMapper.overLappedTimePlans(plan.getCreator(), DeleteFlag.VALID.getCode(), plan.getExecutionTime(), plan.getEndTime(),  plan.getId() == null ? null : plan.getId().toString());
        
		/*if (lapperds == null || lapperds.size() == 0) {
			return null;
		}
		
		List<TargetPlan> res = new LinkedList<>();
		if (plan.getUnit() == TargetPlan.)
		for (TargetPlan p : lapperds) {
			
		}*/
		
		return lapperds;
	}


	@Override
	public int update(TargetPlan plan) {
		
		TargetPlan oldPlan = this.getById(plan);
		
		targetPlanMapper.saveRecord(new TargetPlanAlterRecord(oldPlan));
		
        if (oldPlan.getStartDate().after(new Date())) {
        	oldPlan.setStartDate(plan.getStartDate());
        }
		oldPlan.setPlanName(plan.getPlanName());
		oldPlan.setContent(plan.getContent());
		oldPlan.setExecutionTime(plan.getExecutionTime());
		oldPlan.setEndTime(plan.getEndTime());
		oldPlan.setPeriod(plan.getPeriod());
		oldPlan.setUnit(plan.getUnit());
		return super.update(oldPlan);
	}

	@Override
	public List<TargetPlanAlterRecord> recordsList(Integer id, Integer creatorId) {
		
		return targetPlanMapper.getRecordsList(id, creatorId);
	}

	@Override
	public List<TargetPlan> unCompleteList(int creator) {
		
		return targetPlanMapper.unCompleteList(creator, DeleteFlag.VALID.getCode());
	}

	@Override
	public void updateCountBatch(List<TargetPlan> willPlan) {
		targetPlanMapper.updateCountBatch(willPlan);
	}
	
	
	public long getTotalCountFromPlan(TargetPlan plan) {
		LocalDate startDate =  plan.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
         endDate = plan.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		switch (plan.getUnit()) {
		case TargetPlan.DAY:
			return ChronoUnit.DAYS.between(startDate, endDate);
        case TargetPlan.WEEK:
        	long dura = ChronoUnit.WEEKS.between(startDate, endDate);
        	return (startDate.getDayOfWeek().getValue() > endDate.getDayOfWeek().getValue() ? dura + 1 : dura) / plan.getPeriod();
		default:
			long d2 = ChronoUnit.MONTHS.between(startDate, endDate);
			return (startDate.getDayOfMonth() > endDate.getDayOfMonth() ? d2 + 1 : d2) / plan.getPeriod();
		}
	}
	
	
   
}