package com.hxm.client.module.player.handler;


import com.hxm.common.core.annotion.SocketCommand;
import com.hxm.common.core.annotion.SocketModule;
import com.hxm.common.module.ModuleId;
import com.hxm.common.module.user.UserCmd;

/**
 * 玩家模块
 * @author -琴兽-
 *
 */
@SocketModule(module = ModuleId.USER)
public interface UserHandler {
	
	
//	/**
//	 * 创建并登录帐号
//	 * @param resultCode
//	 * @param data {@link null}
//	 */
//	@SocketCommand(cmd = PlayerCmd.REGISTER_AND_LOGIN)
//	public void registerAndLogin(int resultCode, byte[] data);
	

	/**
	 * 创建并登录帐号
	 * @param resultCode
	 * @param data {@link null}
	 */
	@SocketCommand(cmd = UserCmd.LOGIN)
	public void login(int resultCode, byte[] data);

}
