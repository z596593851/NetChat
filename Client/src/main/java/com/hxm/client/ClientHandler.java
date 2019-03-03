package com.hxm.client;

import com.hxm.client.scanner.Invoker;
import com.hxm.client.scanner.InvokerHoler;
import com.hxm.client.swing.SwingTest;
import com.hxm.common.core.model.Response;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * 消息接受处理类
 * @author -琴兽-
 *
 */
public class ClientHandler extends SimpleChannelHandler {
	
	/**
	 * 界面
	 */
	private SwingTest swingTest;
	
	public ClientHandler(SwingTest swingTest) {
		this.swingTest = swingTest;
	}

	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

		Response response = (Response)e.getMessage();
	
		handlerResponse(response);
	}
	
	
	/**
	 * 消息处理
	 * @param channelId
	 * @param request
	 */
	private void handlerResponse(Response response){
		
		//获取命令执行器
		Invoker invoker = InvokerHoler.getInvoker(response.getModule(), response.getCmd());
		if(invoker != null){
			try {
				invoker.invoke(response.getStateCode(), response.getData());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//找不到执行器
			System.out.println(String.format("module:%s  cmd:%s 找不到命令执行器", response.getModule(), response.getCmd()));
		}
	}

	/**
	 * 断开链接
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		swingTest.getTips().setText("与服务器断开连接~~~");
	}
}
