package com.hxm.server.module.user.service;


import com.hxm.common.core.exception.ErrorCodeException;
import com.hxm.common.core.model.ResultCode;
import com.hxm.common.core.session.Session;
import com.hxm.common.core.session.SessionManager;
import com.hxm.common.module.user.response.UserResponse;
import com.hxm.server.module.user.entity.User;
import org.springframework.stereotype.Component;

/**
 * 玩家服务
 * 
 * @author -琴兽-
 * 
 */
@Component
public class UserServiceImpl implements UserService {




	@Override
	public UserResponse login(Session session, int userId) {

		// 判断当前会话是否已登录
		if (session.getAttachment() != null) {
			throw new ErrorCodeException(ResultCode.HAS_LOGIN);
		}



		// 判断是否在其他地方登录过
		boolean onlinePlayer = SessionManager.isOnlineUser(userId);
		if (onlinePlayer) {
			Session oldSession = SessionManager.removeSession(userId);
			oldSession.removeAttachment();
			// 踢下线
			oldSession.close();
		}

		// 加入在线玩家会话
		if (SessionManager.putSession(userId, session)) {
			session.setAttachment(new User(userId));
		} else {
			throw new ErrorCodeException(ResultCode.LOGIN_FAIL);
		}

		// 创建Response传输对象返回
		UserResponse userResponse = new UserResponse();
		userResponse.setUserId(userId);
		return userResponse;
	}

}
