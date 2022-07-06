package com.jungle;

import net.sf.cglib.proxy.Enhancer;

public class Appication {
    public static void main(String[] args) {
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
}
