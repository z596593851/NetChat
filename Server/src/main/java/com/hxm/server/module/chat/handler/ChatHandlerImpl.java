package com.hxm.server.module.chat.handler;


import com.hxm.common.core.exception.ErrorCodeException;
import com.hxm.common.core.model.Result;
import com.hxm.common.core.model.ResultCode;
import com.hxm.common.module.chat.request.PrivateChatRequest;
import com.hxm.common.module.chat.request.PublicChatRequest;
import com.hxm.server.module.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ChatHandlerImpl implements ChatHandler{
	
	@Autowired
	private ChatService chatService;

	@Override
	public Result<?> publicChat(int userId, byte[] data) {
		try {
			//反序列化--解包
			PublicChatRequest request = new PublicChatRequest();
			request.readFromBytes(data);
			
			//参数校验
			if(StringUtils.isEmpty(request.getContext())){
				return Result.ERROR(ResultCode.AGRUMENT_ERROR);
			}
			
			//执行业务
			chatService.publicChat(userId, request.getContext());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS();
	}

	@Override
	public Result<?> privateChat(int userId, byte[] data) {
		try {
			//反序列化
			PrivateChatRequest request = new PrivateChatRequest();
			request.readFromBytes(data);
			
			//参数校验
			if(StringUtils.isEmpty(request.getContext()) || request.getTargetUserId() <= 0){
				return Result.ERROR(ResultCode.AGRUMENT_ERROR);
			}
			
			//执行业务
//			System.out.println(request.getContext());
			chatService.privateChat(userId, request.getTargetUserId(), request.getContext());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS();
	}
}
