package com.oo.businessplan.basic.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.additional.pojo.Msg;

public interface MsgMapper {
	
	
	 @Insert("INSERT INTO verificationcode (type,code,phoneNo,createTime,validTime) VALUES(#{type},#{code},#{phoneNo},NOW(),#{validTime})")
	 void  insertVerificationCode(Msg msg);
	 
	 @Select("SELECT * FROM verificationcode WHERE phoneNo=#{phoneNo} AND type=#{type,jdbcType=TINYINT} "
	 		+ "AND delflag=#{delflag,jdbcType=TINYINT} ORDER BY createTime desc LIMIT 1")
	 Msg findVerificationCodeByPhoneNo(String phoneNo,byte delflag,byte type);
	 
	 @Update("UPDATE verificationcode set delflag=#{delflag,jdbcType=TINYINT} WHERE phoneNO=#{phoneNo} AND type=#{type,jdbcType=TINYINT}")
	 void delete(Msg msg);

}
