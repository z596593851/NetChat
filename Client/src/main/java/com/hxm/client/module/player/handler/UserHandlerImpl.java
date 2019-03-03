package com.hxm.client.module.player.handler;


import com.hxm.client.swing.ResultCodeTip;
import com.hxm.client.swing.SwingTest;
import com.hxm.common.core.model.ResultCode;
import com.hxm.common.module.user.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 玩家模块
 * @author -琴兽-
 *
 */
@Component
public class UserHandlerImpl implements UserHandler {
	

	@Autowired
	private SwingTest swingTest;

	@Autowired
	private ResultCodeTip resultCodeTip;

//	@Override
//	public void registerAndLogin(int resultCode, byte[] data) {
//		if(resultCode == ResultCode.SUCCESS){
//			PlayerResponse playerResponse = new PlayerResponse();
//			playerResponse.readFromBytes(data);
//
//			swingclient.setPlayerResponse(playerResponse);
//			swingclient.getTips().setText("注册登录成功");
//		}else{
//			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
//		}
//	}

	@Override
	public void login(int resultCode, byte[] data) {
		if(resultCode == ResultCode.SUCCESS){
			UserResponse userResponse = new UserResponse();
			userResponse.readFromBytes(data);

			swingTest.setUserResponse(userResponse);
			swingTest.getTips().setText("登录成功");
		}else{
			swingTest.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}
}
