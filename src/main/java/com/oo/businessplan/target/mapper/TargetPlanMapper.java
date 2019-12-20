package com.oo.businessplan.target.mapper;

import java.sql.Time;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.oo.businessplan.basic.mapper.BaseMapper;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.pojo.entity.TargetPlanAlterRecord;
import com.oo.businessplan.target.pojo.form.TargetPlanForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-10-24 16:38:41
 */
public interface TargetPlanMapper extends BaseMapper<TargetPlan> {
	
	public List<TargetPlan> getListByTarget(TargetPlanForm form);
	
	/**
	 * 查看执行时间重叠的计划
	 * @param creator
	 * @param delflag
	 * @param executionTime
	 * @param endTime
	 * @return
	 */
	public List<TargetPlan> overLappedTimePlans(@Param("creator")int creator, @Param("delflag")Byte delflag
			, @Param("executionTime")Time executionTime, @Param("endTime")Time endTime, @Param("filter")String filterIds);
	
	/**
	 * 保存修改记录
	 * @param record
	 * @return
	 */
	public int saveRecord(TargetPlanAlterRecord record);
	
	@Select("select re.* from target_plan_alter_record re left join target_plan p on re.targetPlanId = p.id where p.id = #{targetPlanId} and p.creator=#{creator}")
	public List<TargetPlanAlterRecord> getRecordsList(@Param("targetPlanId")int planId, @Param("creator")int creatorId);
	
	/**
	 * 查询当天没有完成的动作的计划
	 * @param creator
	 * @param delflag
	 * @return
	 */
	public List<TargetPlan> unCompleteList(@Param("creator")int creator, @Param("delflag")byte delflag);

	/**
	 * 批量更新count
	 * @param list
	 */
	public void updateCountBatch(List<TargetPlan> list);
}