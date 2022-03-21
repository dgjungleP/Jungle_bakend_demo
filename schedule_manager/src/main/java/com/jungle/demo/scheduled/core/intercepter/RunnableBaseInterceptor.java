package com.jungle.demo.scheduled.core.intercepter;

import com.jungle.demo.scheduled.core.intercepter.strengthen.BaseStrengthen;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 通过cglib代理增强
 */
public class RunnableBaseInterceptor implements MethodInterceptor {
    private MyScheduledRunnable runnable;
    private BaseStrengthen strengthen;

    public RunnableBaseInterceptor(Object bean, MyScheduledRunnable runnable) {
        this.runnable = runnable;
        if (BaseStrengthen.class.isAssignableFrom(bean.getClass())) {
            this.strengthen = (BaseStrengthen) bean;
        } else {
            throw new RuntimeException("Sorry the bean:" + bean + " is not a BaseStrengthen type");
        }
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result;
        //TODO: 为什么这里是执行invoke方法的时候调用
        if ("invoke".equals(method.getName())) {
            strengthen.before(o, method, objects);
            try {
                result = runnable.invoke();

            } catch (Exception e) {
                strengthen.exception(o, method, objects);
                throw new RuntimeException("Have a exception in running the invoke method");
            } finally {
                strengthen.afterFinally(o, method, objects);
            }
            strengthen.after(o, method, objects);
        } else {
            result = methodProxy.invokeSuper(o, objects);
        }
        return result;
    }
}
