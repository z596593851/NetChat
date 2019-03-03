package com.hxm.server.scanner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/27.
 */
public class InvokerHolder {

    public static Map<Short,Map<Short,Invoker>> invokers= new HashMap<Short,Map<Short,Invoker>>();

    public static void addInvoker(short module,short cmd,Invoker invoker){
        Map<Short,Invoker> map=invokers.get(module);
        if(map==null){
            map=new HashMap<>();
            invokers.put(module,map);
        }
        map.put(cmd,invoker);
    }

    public static Invoker getInvoker(short module,short cmd){
        Map<Short,Invoker>map=invokers.get(module);
        if(map!=null){
            return map.get(cmd);
        }
        return null;
    }
}
