package com.jungle.demo.scheduled.common.util.proxy;

import com.jungle.demo.scheduled.core.intercepter.RunnableBaseInterceptor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public class ProxyUtils {
    /**
     * 通过cglib进行代理
     *
     * @param pointClass
     * @param methodInterceptor
     * @param <T>
     * @return
     */
    public static <T> T getInstance(Class<T> pointClass, MethodInterceptor methodInterceptor) {
        //字节码增强其，用于创建动态代理类
        Enhancer enhancer = new Enhancer();
        //代理莫表对象
        enhancer.setSuperclass(pointClass);
        //回调类，在方法调用的时候会调用回调方法的intercept方法
        enhancer.setCallback(methodInterceptor);
        //创建代理类
        Object result = enhancer.create();
        return (T) result;
    }
}
