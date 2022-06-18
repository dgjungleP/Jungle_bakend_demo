package com.jungle;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.junit.jupiter.api.Test;

public class CglibEnhancerTest {

    @Test
    public void newEnhancerTest() {
        Enhancer enhancer = new Enhancer();
        //设置需要代理的类
        enhancer.setSuperclass(Student.class);
        //设置代理的回调信息
        enhancer.setCallback(new ProxyInterceptor());

        Student student = (Student) enhancer.create();
        student.readBook();
        student.eat();
        System.out.println(student);
    }

    @Test
    public void newEnhancerFilterProxyTest() {
        Enhancer enhancer = new Enhancer();
        //设置需要代理的类
        enhancer.setSuperclass(Student.class);
        //设置代理的回调信息
        enhancer.setCallbacks(new Callback[]{new ProxyInterceptor(), NoOp.INSTANCE});
        enhancer.setCallbackFilter(new MethodCallbackFilter());
        Student student = (Student) enhancer.create();
        student.readBook();
        student.eat();
        System.out.println(student);
    }

    @Test
    public void newEnhancerCallbackHelperTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Student.class);
        MyCallbackHelper helper = new MyCallbackHelper(Student.class, new Class[0]);

        enhancer.setCallbacks(helper.getCallbacks());
        enhancer.setCallbackFilter(helper);
        Student student = (Student) enhancer.create();
        student.readBook();
        student.eat();
        System.out.println(student);
    }
}
