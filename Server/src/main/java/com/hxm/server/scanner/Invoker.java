package com.hxm.server.scanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2019/2/27.
 */
public class Invoker {

    private Object target;
    private Method method;

    public static Invoker valueOf(Object target,Method method){
        Invoker invoker=new Invoker();
        invoker.setTarget(target);
        invoker.setMethod(method);
        return invoker;
    }

    public Object invoke(Object... paramValues){


        try {
            return method.invoke(target,paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
