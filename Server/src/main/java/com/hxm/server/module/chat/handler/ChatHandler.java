package com.hxm.server.module.chat.handler;


import com.hxm.common.core.annotion.SocketCommand;
import com.hxm.common.core.annotion.SocketModule;
import com.hxm.common.core.model.Result;
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
	 * 广播消息
	 * @param playerId
	 * @param data {@link PublicChatRequest}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.PUBLIC_CHAT)
	public Result<?> publicChat(int userId, byte[] data);
	
	
	
	/**
	 * 私人消息
	 * @param playerId
	 * @param data {@link PrivateChatRequest}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.PRIVATE_CHAT)
	public Result<?> privateChat(int userId, byte[] data);
}
