package com.oo.businessplan.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.AddErrorException;
import com.oo.businessplan.article.mapper.NovelMapper;
import com.oo.businessplan.article.mapper.PortionMapper;
import com.oo.businessplan.article.service.PortionService;
import com.oo.businessplan.article.pojo.entity.Portion;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-20 10:21:41
 */
@Service("portionService")
public class PortionServiceImpl extends BaseServiceImpl<Portion> implements PortionService {

    @Autowired
    PortionMapper portionMapper;
    
    @Autowired
    NovelMapper novelMapper;
    
	/**
	 * 1.确定分卷的卷号
	 * 2.维护小说的分卷总数值
	 */
    @Override
	@Transactional(rollbackFor= {Exception.class})
	public void add(Portion t) throws AddErrorException {
		if (t.getNumber() == null) {
			int num = portionMapper.getNovelMaxPortion(t.getNovelId(), DeleteFlag.VALID.getCode()) + 1;
			t.setNumber((double)num);
		}
		novelMapper.updatePortionNum(t.getNovelId(), 1);
		super.add(t);
	}

	/**
	 * 1.判断当前分卷是否还存在未删除的章节
	 * 2.维护萧索的分卷总数值
	 * 3.删除分卷
	 */
	@Override
	public boolean delete(Portion t) {
		
		//1.
		
		//2.
		novelMapper.updatePortionNum(t.getNovelId(), -1);
		
		return super.delete(t);
	}

	@Override
	public List<Portion> getList(Portion t) {
		
		return t != null && t.getId() != null ? portionMapper.getExpandList(t) : super.getList(t);
	}

	@Override
	public List<Portion> getExpandList(Portion portion) {
		
		return portionMapper.getExpandList(portion);
	}
	
	
    
	
}