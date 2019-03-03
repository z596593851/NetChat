package com.hxm.common.module.chat.request;


import com.hxm.common.core.serial.Serializer;

/**
 * 私聊请求
 * @author -琴兽-
 *
 */
public class PrivateChatRequest extends Serializer {
	
	/**
	 * 要向哪个会话发消息
	 */
	private int targetUserId;
	
	/**
	 * 内容
	 */
	private String context;

	public int getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(int targetUserId) {
		this.targetUserId = targetUserId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	protected void read() {
		this.targetUserId=readInt();
		this.context = readString();
	}

	@Override
	protected void write() {
		writeInt(targetUserId);
		writeString(context);
	}
}
