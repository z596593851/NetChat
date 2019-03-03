package com.hxm.client.module.chat.handler;


import com.hxm.client.swing.ResultCodeTip;
import com.hxm.client.swing.SwingTest;
import com.hxm.common.core.model.ResultCode;
import com.hxm.common.module.chat.response.ChatResponse;
import com.hxm.common.module.chat.response.ChatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatHandlerImpl implements ChatHandler{


	@Autowired
	private SwingTest swingTest;

	@Autowired
	private ResultCodeTip resultCodeTip;

	@Override
	public void publicChat(int resultCode, byte[] data) {
		if(resultCode == ResultCode.SUCCESS){
			swingTest.getTips().setText("发送成功");
		}else{
			swingTest.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@Override
	public void privateChat(int resultCode, byte[] data) {
		if(resultCode == ResultCode.SUCCESS){
			swingTest.getTips().setText("发送成功");
		}else{
			swingTest.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@Override
	public void receieveMessage(int resultCode, byte[] data) {
		
		ChatResponse chatResponse = new ChatResponse();
		chatResponse.readFromBytes(data);
		
		if(chatResponse.getChatType()== ChatType.PUBLIC_CHAT){
			StringBuilder builder = new StringBuilder();
			builder.append("[");
			builder.append(chatResponse.getSendUserId());
			builder.append("]");
			builder.append(" 说:\n");
			builder.append(chatResponse.getMessage());
			builder.append("\n\n");
//			System.out.println(chatResponse.getSendUserId());
//			System.out.println(chatResponse.getMessage());

			swingTest.getChatContext().append(builder.toString());
		}else if(chatResponse.getChatType()==ChatType.PRIVATE_CHAT){
			StringBuilder builder = new StringBuilder();
			
			if(swingTest.getUserResponse().getUserId() == chatResponse.getSendUserId()){
				builder.append("您悄悄对 ");
				builder.append("[");
				builder.append(chatResponse.getTargetUserId());
				builder.append("]");
				builder.append(" 说:\n");
				builder.append(chatResponse.getMessage());
				builder.append("\n\n");
			}else{
				builder.append("[");
				builder.append(chatResponse.getSendUserId());
				builder.append("]");
				builder.append(" 悄悄对你说:\n");
				builder.append(chatResponse.getMessage());
				builder.append("\n\n");
			}

			swingTest.getChatContext().append(builder.toString());
		}
	}
}
