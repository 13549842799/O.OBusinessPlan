package com.oo.businessplan.article.service;

import com.oo.businessplan.basic.service.BaseService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.oo.businessplan.article.pojo.entity.Portion;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-20 10:21:41
 */
public interface PortionService extends BaseService<Portion> {
	
	List<Portion> getExpandList(Portion portion);
	
	/**
	 * 对小说设置小说相关 分卷
	 * @param novelId
	 */
    default void addDefaultPosition(int novelId, int creator) {
    	Portion worksInfo = new Portion();
    	worksInfo.setTitle("作品相关");
    	worksInfo.setCreator(creator);
    	worksInfo.setCreateTime(new Timestamp(new Date().getTime()));
    	worksInfo.setNovelId(novelId);
    	worksInfo.setNumber(0d);
    	worksInfo.setType(Portion.WORKSINFO);
    	this.add(worksInfo);
	}
}