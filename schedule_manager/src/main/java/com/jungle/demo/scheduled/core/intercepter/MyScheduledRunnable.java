package com.jungle.demo.scheduled.core.intercepter;

import com.jungle.demo.scheduled.common.util.proxy.Chain;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class MyScheduledRunnable {

    private Method method;

    private Object bean;
    private Chain chain;


    public Object invoke() {
        return null;
    }
}
