package com.hxm.server.scanner;


import com.hxm.common.core.annotion.SocketCommand;
import com.hxm.common.core.annotion.SocketModule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/27.
 */
@Component
public class Scanner implements InitializingBean,ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,Object> beans=applicationContext.getBeansWithAnnotation(SocketModule.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Class<?>[]interfaces=entry.getValue().getClass().getInterfaces();
            for(Class<?> interFace : interfaces){
                SocketModule socketModule=interFace.getAnnotation(SocketModule.class);
                if(socketModule==null)
                    continue;
                Method[] methods=interFace.getMethods();
                if(methods!=null&&methods.length>0)
                    for(Method method:methods){
                        SocketCommand socketCmd=method.getAnnotation(SocketCommand.class);
                        if(socketCmd==null)
                            continue;

                        short module=socketModule.module();
                        short cmd=socketCmd.cmd();
                        Invoker invoker=Invoker.valueOf(entry.getValue(),method);
                        if(InvokerHolder.getInvoker(module,cmd)==null)
                            InvokerHolder.addInvoker(module,cmd,invoker);
                        else
                            System.out.println("重复注册执行器module:"+module+" cmd:"+cmd);
                    }




            }

        }

//        for (Map.Entry<String, Object> entry : beans.entrySet()) {
//            Method[] methods=entry.getValue().getClass().getMethods();
//            if(methods!=null&&methods.length>0)
//                for(Method method:methods){
//                    SocketCmd socketCmd=method.getAnnotation(SocketCmd.class);
//                    if(socketCmd==null)
//                        continue;
//                    SocketModule socketModule=(SocketModule)entry.getValue();
//                    short module=socketModule.module();
//                    short cmd=socketCmd.cmd();
//                    Invoker invoker=Invoker.valueOf(entry,method);
//                    if(InvokerHolder.getInvoker(module,cmd)==null)
//                        InvokerHolder.addInvoker(module,cmd,invoker);
//                    else
//                        System.out.println("重复注册执行器module:"+module+" cmd:"+cmd);
//
//                }
//
//
//        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
