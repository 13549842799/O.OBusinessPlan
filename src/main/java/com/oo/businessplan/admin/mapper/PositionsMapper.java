package com.oo.businessplan.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.admin.pojo.entity.Positions;


public interface PositionsMapper {
	
	
	@Select("SELECT * FROM positions WHERE poid=#{poid,jdbcType=INTEGER} AND state=IFNULL(#{state,jdbcType=TINYINT},state) AND delflag=#{delflag,jdbcType=TINYINT}")
 	Positions findPositionsById(@Param("poid")int poid,@Param("state")Byte state,@Param("delfalg")Byte delflag );

	@Select("SELECT * FROM positions WHERE pcode=#{dcode,jdbcType=VARCHAR} AND state=IFNULL(#{state,jdbcType=TINYINT},state) AND delflag=#{delflag,jdbcType=TINYINT}")
	Positions findPositionsByCode(@Param("pcode")String pcode,@Param("state")Byte state,@Param("delfalg")Byte delflag);

	@Update("UPDATE positions SET delflag=#{delflag,jdbcType=TINYINT} WHERE poid=#{poid,jdbcType=INTEGER} AND delflag=#{delete,jdbcType=TINYINT}")
	int delete(@Param("poid")int poid,@Param("delflag")byte delflag,@Param("delete")byte delete);
	 
	@Update("UPDATE positions SET state=#{state,jdbcType=TINYINT} WHERE poid=#{poid,jdbcType=INTEGER} AND delflag=#{delete,jdbcType=TINYINT}")
	int alterState(@Param("poid")int poid,@Param("state")byte state,@Param("delflag")byte delflag);
	
	Positions findPositions(Positions positions);
	
	List<Positions> list();
	
	int update(Positions positions);
	
	void insert(Positions positions);

}
