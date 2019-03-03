package com.hxm.common.core.session;

import com.hxm.common.core.model.Response;
import com.hxm.common.core.serial.Serializer;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import java.util.Collections;
import java.util.Set;

/**
 * 会话管理者
 * @author -琴兽-
 *
 */
public class SessionManager {

	/**
	 * 在线会话
	 */
	private static final ConcurrentHashMap<Integer, Session> onlineSessions = new ConcurrentHashMap<>();
	
	/**
	 * 加入
	 * @param userId
	 * @param channel
	 * @return
	 */
	public static boolean putSession(int userId, Session session){
		if(!onlineSessions.containsKey(userId)){
			boolean success = onlineSessions.putIfAbsent(userId, session)== null? true : false;
			return success;
		}
		return false;
	}
	
	/**
	 * 移除
	 * @param userId
	 */
	public static Session removeSession(int userId){
		return onlineSessions.remove(userId);
	}
	
	/**
	 * 发送消息[自定义协议]
	 * @param <T>
	 * @param userId
	 * @param message
	 */
	public static <T extends Serializer> void sendMessage(int userId, short module, short cmd, T message){
		Session session = onlineSessions.get(userId);
		if (session != null && session.isConnected()) {
			Response response = new Response(module, cmd, message.getBytes());
			session.write(response);
		}
	}
	
//	/**
//	 * 发送消息[protoBuf协议]
//	 * @param <T>
//	 * @param playerId
//	 * @param message
//	 */
//	public static <T extends GeneratedMessage> void sendMessage(long playerId, short module, short cmd, T message){
//		Session session = onlineSessions.get(playerId);
//		if (session != null && session.isConnected()) {
//			Response response = new Response(module, cmd, message.toByteArray());
//			session.write(response);
//		}
//	}
	
	/**
	 * 是否在线
	 * @param userId
	 * @return
	 */
	public static boolean isOnlineUser(int userId){
		return onlineSessions.containsKey(userId);
	}
	
	/**
	 * 获取所有在线玩家
	 * @return
	 */
	public static Set<Integer> getOnlineUsers() {
		return Collections.unmodifiableSet(onlineSessions.keySet());
	}
}
