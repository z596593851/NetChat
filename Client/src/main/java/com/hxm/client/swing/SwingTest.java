package com.hxm.client.swing;

import com.hxm.client.Client;
import com.hxm.common.core.model.Request;
import com.hxm.common.module.ModuleId;
import com.hxm.common.module.chat.ChatCmd;
import com.hxm.common.module.chat.request.PrivateChatRequest;
import com.hxm.common.module.chat.request.PublicChatRequest;
import com.hxm.common.module.user.UserCmd;
import com.hxm.common.module.user.request.LoginRequest;
import com.hxm.common.module.user.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xiaoming on 2019/3/1.
 */

@Component
public class SwingTest extends JFrame implements ActionListener {
    @Autowired
    private Client client;
    private UserResponse userResponse;

    private JTextField sendUserId;
    private JTextField targetUserId;
    private JTextArea chatContext;
    private JTextArea message;
    private JButton sendButton;
    private JButton loginButoon;
    private JLabel tips;


    public SwingTest() {
        setTitle("NetChat");    //设置显示窗口标题
        setSize(500,650);    //设置窗口显示尺寸

        JPanel j1 = new JPanel();
        JPanel j2 = new JPanel();
        JPanel j3 = new JPanel();
        JPanel j4 = new JPanel();
        JPanel j5 = new JPanel();
        JPanel j6 = new JPanel();
        JPanel j7 = new JPanel();

        sendUserId = new JTextField(11);
        targetUserId=new JTextField(17);
        chatContext=new JTextArea(20,40);
        message=new JTextArea(7,40);
        tips = new JLabel("");
        tips.setBounds(76, 488, 200, 15);
        tips.setForeground(Color.red);

        loginButoon=new JButton("登录");
        loginButoon.setActionCommand("login");
        loginButoon.addActionListener(this);

        sendButton=new JButton("发送");
        sendButton.setActionCommand("send");
        sendButton.addActionListener(this);



        j1.add(new JLabel("账户："));
        j1.add(sendUserId);
        j1.add(loginButoon);
        j2.add(new JLabel("发给："));
        j2.add(targetUserId);
        j3.add(chatContext);
        j4.add(sendButton);
        j5.add(tips);
        j6.add(message);


        j7.add(j1);
        j7.add(j5);
        j7.add(j2);
        j7.add(j3);
        j7.add(j6);
        j7.add(j4);


        add(j7);
        setVisible(true);    //设置窗口是否可见
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "login":
                if(this.sendUserId.getText().isEmpty()){
                    tips.setText("用户名不能为空");
                    return;
                }

                try {
                    int sendUserId=Integer.parseInt(this.sendUserId.getText());
                    LoginRequest loginRequest=new LoginRequest();
                    loginRequest.setUserId(sendUserId);
                    Request request = Request.valueOf(ModuleId.USER, UserCmd.LOGIN, loginRequest.getBytes());
                    client.sendRequest(request);
                } catch (Exception e) {
                    tips.setText("无法连接服务器");
                    e.printStackTrace();
                }
                tips.setText("登录成功");
                break;
            case "send":

                try {
                    if(this.targetUserId.getText().isEmpty()&&!this.message.getText().isEmpty()){
                        //公聊
                        PublicChatRequest publicChatRequest = new PublicChatRequest();
                        publicChatRequest.setContext(message.getText());
                        Request request = Request.valueOf(ModuleId.CHAT, ChatCmd.PUBLIC_CHAT, publicChatRequest.getBytes());
                        client.sendRequest(request);
                    }else{
                        if(this.message.getText().isEmpty()){
                            tips.setText("发送消息不能为空");
                            return;
                        }
                        //私聊
//                        System.out.println("私聊"+this.message.getText());
                        PrivateChatRequest privateChatRequest=new PrivateChatRequest();
                        privateChatRequest.setTargetUserId(Integer.parseInt(this.targetUserId.getText()));
                        privateChatRequest.setContext(this.message.getText());
                        Request request = Request.valueOf(ModuleId.CHAT, ChatCmd.PRIVATE_CHAT, privateChatRequest.getBytes());
                        client.sendRequest(request);



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    tips.setText("无法连接服务器");
                }
                break;
            default:
                break;
        }

    }

    public JTextArea getChatContext() {
        return chatContext;
    }
    public JLabel getTips() {
        return tips;
    }

    public UserResponse getUserResponse(){
        return userResponse;
    }
    public void setUserResponse(UserResponse userResponse){
        this.userResponse=userResponse;
    }

//    public static void main(String[] agrs) {
//        new SwingTest();    //创建一个实例化对象
//    }
}
