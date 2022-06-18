package com.jungle;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import javax.sound.midi.Soundbank;
import java.lang.reflect.Method;

public class ProxyInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = null;
        System.out.println(method.getName());
        System.out.println("method invoke before...");
        result = proxy.invokeSuper(obj, args);
        System.out.println("method invoke after...");
        return result;
    }
}
