package com.oo.businessplan.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.oo.businessplan.admin.pojo.entity.AcceptMessage;
import com.oo.businessplan.basic.mapper.BaseMapper;

public interface AcceptMessageMapper extends BaseMapper<AcceptMessage> {
	
	/**
	 * 查询目标收信人的特定类型的消息的状态为未读的数量
	 * @param id
	 * @param type
	 * @param state
	 * @param delfalg
	 * @return
	 */
	int unReadCount(@Param("accepterId")int id,@Param("type")Byte type,
			@Param("state")Byte state,@Param("delflag")Byte delfalg);
	
	List<Map<String,Object>> getAdminMessage(@Param("accepterId")int id,
			@Param("type")Byte type,@Param("state")Byte state,
			@Param("delflag")Byte delfalg);

}
