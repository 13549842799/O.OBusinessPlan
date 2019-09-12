package com.oo.businessplan.article.pojo.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.oo.businessplan.article.pojo.entity.Section;

public class SectionForm extends Section {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3088312806085564839L;

	private String novelName;
	
	private String portionName;

	public String getNovelName() {
		return novelName;
	}

	public void setNovelName(String novelName) {
		this.novelName = novelName;
	}

	public String getPortionName() {
		return portionName;
	}

	public void setPortionName(String portionName) {
		this.portionName = portionName;
	}
	
	public String getCreateTimeStr() {
		if (this.getCreateTime() == null) {
			return null;
		}
		long distance = (new Date().getTime() - this.getCreateTime().getTime())/1000;
		if (distance < 60) {
			return "刚刚";
		}
		distance = distance/60; //分钟
		if (distance <60) {
			return distance + "分钟前";
		}
		distance = distance/60; //小时
		if (distance < 24) {
			return distance + "小时前";
		}
		distance = distance/24;
		if (distance < 30) {
			return distance + "天前";
		}
		distance = distance/30;
		if (distance < 12) {
			return distance + "个月前";
		}
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        return sdf.format(this.getCreateTime());
	}

}
