package com.oo.businessplan.target.pojo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oo.businessplan.basic.entity.CreatorWithStateEntity;
import com.oo.businessplan.common.valid.EnableCheckOut;
import com.oo.businessplan.common.valid.FieldMeta;

/**
 * 目标模块
 * 记录使用者未来要达成的目标，包括目标内容，为什么要达成这个目标，怎样才算达成目标，紧急程度，目标类型还有相关时间。
 * 首先用户打开app，由 个人中心 - 目标管理 。
 * 1.记录目标
 * 2.记录计划
 * 3.记录每次计划执行结果
 * 4.实时提醒（应该执行某个计划了）
 * 
 * 操作流程：
 * 在个人中心的页面点击我的目标模块，进入目标页面，
 * 目标页面首页显示的是当天准备完成的目标计划，底部是一个新增目标的按钮。
 * 点击新增目标，跳转到表单页面，
 * @author cyz
 *
 */
@EnableCheckOut
public class Target extends CreatorWithStateEntity<Integer> implements Serializable{
	
	public static final byte DRAFT = 0;//草稿
	
	public static final byte RUN = 1;//进行中
	
	public static final byte FINISH = 2;//完成
	
	public static final byte GIVEUP = 3;//放弃

	/**
	 * 
	 */
	private static final long serialVersionUID = 6546368015226396621L;
	
	public Target() {
	}

	public Target(Integer id, Byte delflag) {
		super(id, delflag);
		// TODO Auto-generated constructor stub
	}

	public Target(Integer id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 原因
	 */
	@FieldMeta("原因")
	private String cause;
	
	/**
	 * 目标内容
	 */
	@FieldMeta(value="目标内容")
	private String content;
	
	/**
	 * 完成标志，做到什么程度才算完成
	 */
	@FieldMeta(value="完成标志")
	private String finishSign;
	
	/**
	 * 紧急程度  1-远期目标  2-中期目标  3-近期目标  4-紧急目标
	 */
	@FieldMeta(value="紧急等级", max="4", min="1", commonMess="请正确的选择紧急程度")
	private Byte level;
	
	/**
	 * 目标类型 1-生活 2-学习 3-工作
	 */
	@FieldMeta(value="目标类型", max="3", maxMess="请选择合适的目标类型", min="1", minMess="请选择合适的目标类型")
	private Byte type;
	
	/**
	 * 预期完成时间
	 */
	@FieldMeta("预期完成时间")
	private Date expectFinishTime;
	
	/**
	 * 真正的完成时间
	 */
	@FieldMeta(value="真正完成时间", notNull=false)
	private Date realFinishTime;
	
	/**
	 * 是否开启提醒  0-关闭 1-开启
	 */
	private Byte alert = null;
	
	public List<TargetPlan> plans;
	
	public Boolean onAlert() {
		return alert == 1;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFinishSign() {
		return finishSign;
	}

	public void setFinishSign(String finishSign) {
		this.finishSign = finishSign;
	}

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getExpectFinishTime() {
		return expectFinishTime;
	}

	public void setExpectFinishTime(Date expectFinishTime) {
		this.expectFinishTime = expectFinishTime;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getRealFinishTime() {
		return realFinishTime;
	}

	public void setRealFinishTime(Date realFinishTime) {
		this.realFinishTime = realFinishTime;
	}

	public Byte getAlert() {
		return alert;
	}

	public void setAlert(Byte alert) {
		this.alert = alert;
	}

	public List<TargetPlan> getPlans() {
		return plans;
	}

	public void setPlans(List<TargetPlan> plans) {
		this.plans = plans;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
	
	public String getLevelName() {
		if (this.level == null) {
			return "未选择";
		}
		switch (this.level) {
		case 1:
			return "远期目标";
		case 2:
			return "中期目标";
		case 3:
			return "近期目标";
		case 4:
			return "紧急目标";
		default:
			return "数据错误";
		}
	}
	
	public long getLeftOverTime() {
		if (this.expectFinishTime == null) {
			return 0;
		}
		long dis = this.expectFinishTime.getTime() - System.currentTimeMillis();
        if (dis < 0) {
        	return 0;
        }
		return (dis/1000)/60/60/24;
	}
	
	public String getTypeName() {
		if (this.type == null) {
			return "未选择";
		}
		switch (this.type) {
		case 1:
			return "生活";
        case 2:
			return "学习";
        case 3:
	        return "工作";
		}
		return "";
	}
	
	public String getStateName() {
		if (this.getState() == null) {
			return "草稿";
		}
		switch (this.getState()) {
		case 0:
		    return "草稿";
		case 1:
		    return "进行中";
		case 2:
		    return "完成";
		case 3:
		    return "放弃";
		}
		return null;
	}
	
}
