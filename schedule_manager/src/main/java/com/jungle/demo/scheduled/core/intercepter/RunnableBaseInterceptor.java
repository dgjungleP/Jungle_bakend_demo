package com.jungle.demo.scheduled.core.intercepter;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class RunnableBaseInterceptor implements MethodInterceptor {
    public RunnableBaseInterceptor(Object bean, MyScheduledRunnable runnable) {
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }
}
