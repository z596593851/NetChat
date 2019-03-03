package com.hxm.common.module.user.response;

import com.hxm.common.core.serial.Serializer;

/**
 * 玩家信息
 * @author -琴兽-
 *
 */
public class UserResponse extends Serializer{
	
	
	/**
	 * id
	 */
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
