package com.oo.businessplan.common.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.ValueOperations;

import com.oo.businessplan.additional.pojo.Msg;

public class RedisMsgManager extends RedisManager {

	@Resource(name="stringRedisTemplate")
	private ValueOperations<String, String> vop;
	
	/**
	 * 获取保存进redis中所用的key
	 * @param phone 手机号
	 * @param type 验证码类型
	 * @return
	 */
	private String key(String phone, byte type) {
		return "msg_" + String.valueOf(type) + "_" + phone;
	}
	
	/**
	 * 使用默认的保存时间
	 * @param message
	 */
	public void saveMsg(Msg message) {
		this.saveMsg(expired, timeUnit, message);
	}
	
	/**
	 * 向redis保存短信
	 * @param expired
	 * @param timeUnit
	 * @param message
	 */
	public void saveMsg(long expired, TimeUnit timeUnit, Msg message) {
		if (message == null) {
			return;
		}
		vop.set(key(message.getPhoneNo(), message.getType()), message.getCode(), expired, timeUnit);
	}
	
	/**
	 * 获取对应的验证码
	 * @param phone
	 * @param type
	 * @return
	 */
	public String msgCode(String phone, byte type) {
		return vop.get(this.key(phone, type));
	}
	
	/**
	 * 删除验证码
	 * @param phone
	 * @param type
	 */
	public void deleteCode(String phone, byte type) {
		this.stringRedisTemplate.delete(this.key(phone, type));
	}
	
}
