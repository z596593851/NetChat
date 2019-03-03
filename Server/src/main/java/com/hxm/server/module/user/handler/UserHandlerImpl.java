package com.hxm.server.module.user.handler;

import com.hxm.common.core.exception.ErrorCodeException;
import com.hxm.common.core.model.Result;
import com.hxm.common.core.model.ResultCode;
import com.hxm.common.core.session.Session;
import com.hxm.common.module.user.request.LoginRequest;
import com.hxm.common.module.user.response.UserResponse;
import com.hxm.server.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 玩家模块
 * @author -琴兽-
 *
 */
@Component
public class UserHandlerImpl implements UserHandler {
	
	@Autowired
	private UserService userService;


	@Override
	public Result<UserResponse> login(Session session, byte[] data) {
		UserResponse result = null;
		try {
			//反序列化
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.readFromBytes(data);
			
			//参数判空
			if(StringUtils.isEmpty(loginRequest.getUserId())){
				return Result.ERROR(ResultCode.PLAYERNAME_NULL);
			}
			
			//执行业务
			result = userService.login(session, loginRequest.getUserId());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS(result);
	}

}
