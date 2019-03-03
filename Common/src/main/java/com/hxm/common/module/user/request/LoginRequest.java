package com.hxm.common.module.user.request;


import com.hxm.common.core.serial.Serializer;

/**
 * 登录请求
 * @author -琴兽-
 *
 */
public class LoginRequest extends Serializer {
	
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	protected void read() {
		this.userId=readInt();
	}

	@Override
	protected void write() {
		writeInt(userId);
	}
}
