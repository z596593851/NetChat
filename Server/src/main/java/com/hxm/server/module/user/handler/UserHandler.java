package com.hxm.server.module.user.handler;


import com.hxm.common.core.annotion.SocketCommand;
import com.hxm.common.core.annotion.SocketModule;
import com.hxm.common.core.model.Result;
import com.hxm.common.core.session.Session;
import com.hxm.common.module.ModuleId;
import com.hxm.common.module.user.UserCmd;
import com.hxm.common.module.user.response.UserResponse;

/**
 * 玩家模块
 * @author -琴兽-
 *
 */
@SocketModule(module = ModuleId.USER)
public interface UserHandler {
	
	
//	/**
//	 * 创建并登录帐号
//	 * @param session
//	 * @param data {@link RegisterRequest}
//	 * @return
//	 */
//	@SocketCommand(cmd = UserCmd.REGISTER_AND_LOGIN)
//	public Result<UserResponse> registerAndLogin(Session session, byte[] data);
	

	/**
	 * 登录帐号
	 * @param session
	 * @param data {@link LoginRequest}
	 * @return
	 */
	@SocketCommand(cmd = UserCmd.LOGIN)
	public Result<UserResponse> login(Session session, byte[] data);

}
