package com.hxm.common.module.chat.response;


import com.hxm.common.core.serial.Serializer;

/**
 * 聊天消息
 * @author -琴兽-
 *
 */
public class ChatResponse extends Serializer {
	

	private int sendUserId;

	private int targetUserId;
	
	/**
	 * 消息类型
	 * 0 广播消息
	 * 1 私聊
	 * {@link ChatType}
	 */
	private byte chatType;
	
	/**
	 * 消息
	 */
	private String message;



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public byte getChatType() {
		return chatType;
	}

	public void setChatType(byte chatType) {
		this.chatType = chatType;
	}

	public int getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(int sendUserId) {
		this.sendUserId = sendUserId;
	}

	public int getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(int targetUserId) {
		this.targetUserId = targetUserId;
	}

	@Override
	protected void read() {
		this.sendUserId = readInt();
		this.targetUserId=readInt();
		this.chatType = readByte();
		this.message = readString();
	}

	@Override
	protected void write() {
		writeInt(this.sendUserId);
		writeInt(this.targetUserId);
		writeByte(this.chatType);
		writeString(this.message);
	}
}
                                                  