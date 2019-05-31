package com.oo.businessplan.basic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 这个接口不是查询redis的接口，而是配合拥有redis功能的类，查询数据库相关数据的接口
 * @author cyz
 *
 * @param <T>
 */
public interface RedisCacheMapper<T> extends BaseMapper<T> {
	
	/**
     * 获取目标的对象
     * @param hashKey  获取对象的关键字段
     * @param delflag
     * @param state
     * @return
     */
    public T getByStr(Map<String, Object> params);
    
    /**
     * 
     * @param key
     * @param delflag
     * @param state
     * @return
     */
    public List<T> getListByStr(@Param("key")String key,@Param("delflag")byte delflag,@Param("state")Byte state );

}
