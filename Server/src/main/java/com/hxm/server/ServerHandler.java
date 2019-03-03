package com.hxm.server;


import com.hxm.common.core.model.Request;
import com.hxm.common.core.model.Response;
import com.hxm.common.core.model.Result;
import com.hxm.common.core.model.ResultCode;
import com.hxm.common.core.serial.Serializer;
import com.hxm.common.core.session.Session;
import com.hxm.common.core.session.SessionImpl;
import com.hxm.common.core.session.SessionManager;
import com.hxm.common.module.ModuleId;
import com.hxm.server.module.user.entity.User;
import com.hxm.server.scanner.Invoker;
import com.hxm.server.scanner.InvokerHolder;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * 消息接受处理类
 * @author xiaoming
 *
 */
public class ServerHandler extends SimpleChannelHandler {
	
	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

		Request request = (Request)e.getMessage();
	
		handlerMessage(new SessionImpl(ctx.getChannel()), request);
	}
	
	
	/**
	 * 消息处理
	 * @param channelId
	 * @param request
	 */
	private void handlerMessage(Session session, Request request){
		
		Response response = new Response(request);
		
		System.out.println("module:"+request.getModule() + "   " + "cmd：" + request.getCmd());
		
		//获取命令执行器
		Invoker invoker = InvokerHolder.getInvoker(request.getModule(), request.getCmd());
		if(invoker != null){
			try {
				Result<?> result = null;
				//假如是玩家模块传入channel参数，否则传入playerId参数
				if(request.getModule() == ModuleId.USER){
					result = (Result<?>)invoker.invoke(session, request.getData());
				//聊天模块
				}else if(request.getModule() == ModuleId.CHAT){
					//每一个Session维护一个channle，每一个channle放一个attachment
					Object attachment = session.getAttachment();
					if(attachment != null){
						User user=(User)attachment;
						result = (Result<?>)invoker.invoke(user.getUserId(), request.getData());
					}else{
						//会话未登录拒绝请求
						response.setStateCode(ResultCode.LOGIN_PLEASE);
						session.write(response);
						return;
					}
				}
				
				//判断请求是否成功
				if(result.getResultCode() == ResultCode.SUCCESS){
					//回写数据
					Object object = result.getContent();
					if(object != null){
						if(object instanceof Serializer){
							Serializer content = (Serializer)object;
							response.setData(content.getBytes());
						}else{
							System.out.println(String.format("不可识别传输对象:%s", object));
						}
					}
					session.write(response);
				}else{
					//返回错误码
					response.setStateCode(result.getResultCode());
					session.write(response);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				//系统未知异常
				response.setStateCode(ResultCode.UNKOWN_EXCEPTION);
				session.write(response);
			}
		}else{
			//未找到执行者
			response.setStateCode(ResultCode.NO_INVOKER);
			session.write(response);
			return;
		}
	}
	
	/**
	 * 断线移除会话
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		Session session = new SessionImpl(ctx.getChannel());
		Object object = session.getAttachment();
		if(object != null){
			User user=(User)object;
			SessionManager.removeSession(user.getUserId());
		}
	}
}
