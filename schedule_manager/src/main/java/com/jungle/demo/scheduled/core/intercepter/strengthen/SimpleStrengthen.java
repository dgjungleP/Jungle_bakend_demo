package com.jungle.demo.scheduled.core.intercepter.strengthen;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

public class SimpleStrengthen implements BaseStrengthen {
    @Override
    public void before(Object bean, Method method, Object[] args) {
        System.out.println("hello");
    }

    @Override
    public void after(Object bean, Method method, Object[] args) {
        System.out.println("hello");
    }

    @Override
    public void exception(Object bean, Method method, Object[] args) {
        System.out.println("hello");
    }

    @Override
    public void afterFinally(Object bean, Method method, Object[] args) {
        System.out.println("hello");
    }
}
