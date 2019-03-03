package com.hxm.client;

import com.hxm.client.swing.SwingTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动函数
 * @author admin
 *
 */
public class ClientMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		SwingTest swing = applicationContext.getBean(SwingTest.class);
		swing.setVisible(true);
	}

}
