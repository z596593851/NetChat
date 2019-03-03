package com.hxm.server.module.user.service;


import com.hxm.common.core.session.Session;
import com.hxm.common.module.user.response.UserResponse;

/**
 * 玩家服务
 * @author -琴兽-
 *
 */
public interface UserService {
	
	
//	/**
//	 * 登录注册用户
//	 * @param playerName
//	 * @param passward
//	 * @return
//	 */
//	public UserResponse registerAndLogin(Session session, String playerName, String passward);
	
	
	/**
	 * 登录
	 * @param playerName
	 * @param passward
	 * @return
	 */
	public UserResponse login(Session session, int userId);

}
