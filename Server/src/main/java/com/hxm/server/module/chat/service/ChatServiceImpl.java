package com.hxm.server.module.chat.service;

import com.hxm.common.core.exception.ErrorCodeException;
import com.hxm.common.core.model.ResultCode;
import com.hxm.common.core.session.SessionManager;
import com.hxm.common.module.ModuleId;
import com.hxm.common.module.chat.ChatCmd;
import com.hxm.common.module.chat.response.ChatResponse;
import com.hxm.common.module.chat.response.ChatType;
import com.hxm.server.module.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 聊天服务
 * @author xiaoming
 *
 */
@Component
public class ChatServiceImpl implements ChatService{
	


	@Override
	public void publicChat(int userId, String content) {

		
		//获取所有在线玩家
		Set<Integer> onlineUsers = SessionManager.getOnlineUsers();
		User user= new User(userId);
		
		//创建消息对象
		ChatResponse chatResponse = new ChatResponse();
		chatResponse.setChatType(ChatType.PUBLIC_CHAT);
		chatResponse.setSendUserId(user.getUserId());
		chatResponse.setMessage(content);

//		System.out.println(content);
		
		//发送消息
		for(int targetUserId : onlineUsers){
			SessionManager.sendMessage(targetUserId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
		}
		
	}

	@Override
	public void privateChat(int userId, int targetUserId, String content) {
		//不能和自己私聊
		if(userId == targetUserId){
			throw new ErrorCodeException(ResultCode.CAN_CHAT_YOUSELF);
		}

		User user= new User(userId);
		
		//判断目标是否存在
//		Player targetPlayer = playerDao.getPlayerById(targetPlayerId);
//		if(targetPlayer == null){
//			throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
//		}
		
		//判断对方是否在线
		if(!SessionManager.isOnlineUser(targetUserId)){
			throw new ErrorCodeException(ResultCode.PLAYER_NO_ONLINE);
		}
		
		//创建消息对象
		ChatResponse chatResponse = new ChatResponse();
		chatResponse.setChatType(ChatType.PRIVATE_CHAT);
		chatResponse.setSendUserId(userId);
		chatResponse.setTargetUserId(targetUserId);
		chatResponse.setMessage(content);
		
		//给目标对象发送消息
		SessionManager.sendMessage(targetUserId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
		//给自己也回一个(偷懒做法)
		SessionManager.sendMessage(userId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
	}
}













