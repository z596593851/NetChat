package com.hxm.client.module.chat.handler;


import com.hxm.common.core.annotion.SocketCommand;
import com.hxm.common.core.annotion.SocketModule;
import com.hxm.common.module.ModuleId;
import com.hxm.common.module.chat.ChatCmd;

/**
 * 聊天
 * @author -琴兽-
 *
 */
@SocketModule(module = ModuleId.CHAT)
public interface ChatHandler {
	
	/**
	 * 发送广播消息回包
	 * @param resultCode
	 * @param data {@link null}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.PUBLIC_CHAT)
	public void publicChat(int resultCode, byte[] data);
	
	/**
	 * 发送私人消息回包
	 * @param resultCode
	 * @param data {@link null}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.PRIVATE_CHAT)
	public void privateChat(int resultCode, byte[] data);
	
	/**
	 * 收到推送聊天信息
	 * @param resultCode
	 * @param data {@link ChatResponse}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.PUSHCHAT)
	public void receieveMessage(int resultCode, byte[] data);
}
