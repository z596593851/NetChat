package com.hxm.client;


import com.hxm.client.swing.SwingTest;
import com.hxm.client.swing.Swingclient;
import com.hxm.common.core.codc.RequestEncoder;
import com.hxm.common.core.codc.ResponseDecoder;
import com.hxm.common.core.model.Request;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * netty客户端入门
 * 
 * @author -琴兽-
 * 
 */
@Component
public class Client {
	
	/**
	 * 界面
	 */
	@Autowired
	private SwingTest swingTest;

	/**
	 * 服务类
	 */
	ClientBootstrap bootstrap = new ClientBootstrap();

	/**
	 * 会话
	 */
	private Channel channel;

	/**
	 * 线程池
	 */
	private ExecutorService boss = Executors.newCachedThreadPool();
	private ExecutorService worker = Executors.newCachedThreadPool();

	/**
	 * 初始化，会在依赖注入完成后被自动调用
	 */
	@PostConstruct
	public void init() {
		// socket工厂
		bootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));

		// 管道工厂
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new ResponseDecoder());
				pipeline.addLast("encoder", new RequestEncoder());
				pipeline.addLast("hiHandler", new ClientHandler(swingTest));
				return pipeline;
			}
		});
	}

	/**
	 * 连接
	 * 
	 * @param ip
	 * @param port
	 * @throws InterruptedException
	 */
	public void connect() throws InterruptedException {

		// 连接服务端
		ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 10102));
		connect.sync();
		channel = connect.getChannel();
	}

	/**
	 * 关闭
	 */
	public void shutdown() {
		bootstrap.shutdown();
	}

	/**
	 * 获取会话
	 * 
	 * @return
	 */
	public Channel getChannel() {
		return channel;
	}
	
	/**
	 * 发送消息
	 * @param request
	 * @throws InterruptedException 
	 */
	public void sendRequest(Request request) throws InterruptedException{
		if(channel == null || !channel.isConnected()){
			connect();
		}
		channel.write(request);
	}
	
}
