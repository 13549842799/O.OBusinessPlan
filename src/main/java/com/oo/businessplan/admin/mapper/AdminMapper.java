package com.oo.businessplan.admin.mapper;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.admin.pojo.form.AdminForm;
import com.oo.businessplan.admin.pojo.page.Padmin;
import com.oo.businessplan.basic.mapper.RedisCacheMapper;

/**
 * 
 * @author cyz
 *
 */
public interface AdminMapper extends RedisCacheMapper<Admin>{
	
	 /**
	  * 更改admin的状态
	  * @param state
	  * @param id
	  * @return
	  */
	 int state(@Param("state")byte state,@Param("key")int id);
	
	 /**
	  * 通过表单参数获取对应的admin相关信息的列表
	  * @param form
	  * @return
	  */
	 List<Padmin> getListByForm(AdminForm form);
     
	 @Select("SELECT COUNT(id) FROM admin WHERE delflag=#{delflag,jdbcType=INTEGER} AND  state=IFNULL(#{state,jdbcType=TINYINT},state)")
	 int searchCount(@Param("state")Byte state,@Param("delflag")Byte delflag);
	 
	 /**
	  * 改变登陆状态
	  * @param accountname
	  * @param autologin
	  */
	 @Update("UPDATE admin SET autologin_mac = #{autologin_mac} WHERE accountname=#{accountname,jdbcType=VARCHAR}")
	 void updateLoginState(@Param("accountname")String accountname,@Param("autologin_mac")String autologin_mac);
	 
	 /**
	  * 返回拥有此昵称的账号的数量
	  * @param nikename
	  * @param code
	  * @return
	  */
	 @Select("SELECT COUNT(id) FROM admin WHERE nikename=#{nikename,jdbcType=VARCHAR} AND delflag=#{delflag,jdbcType=TINYINT}")
	 int validNikeNameExist(@Param("nikename")String nikename,@Param("delflag")byte delflag);

}
