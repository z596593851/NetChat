package com.hxm.server.module.chat.service;
/**
 * 聊天服务
 * @author -琴兽-
 *
 */
public interface ChatService {
	
	
	/**
	 * 群发消息
	 * @param playerId
	 * @param content
	 */
	public void publicChat(int userId, String content);
	
	
	/**
	 * 私聊
	 * @param playerId
	 * @param targetPlayerId
	 * @param content
	 */
	public void privateChat(int userId, int targetUserId, String content);
	
}
