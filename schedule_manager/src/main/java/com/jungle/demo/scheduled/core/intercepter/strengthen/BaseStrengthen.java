package com.jungle.demo.scheduled.core.intercepter.strengthen;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

@Order(Ordered.HIGHEST_PRECEDENCE)
public interface BaseStrengthen {

    void before(Object bean, Method method, Object[] args);

    void after(Object bean, Method method, Object[] args);

    void exception(Object bean, Method method, Object[] args);

    void afterFinally(Object bean, Method method, Object[] args);
}
