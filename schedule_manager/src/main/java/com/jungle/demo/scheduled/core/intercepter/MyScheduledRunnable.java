package com.jungle.demo.scheduled.core.intercepter;

import cn.hutool.core.date.ChineseDate;
import com.jungle.demo.scheduled.common.util.proxy.Chain;
import com.jungle.demo.scheduled.common.util.proxy.Point;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class MyScheduledRunnable {

    private Method method;

    private Object bean;
    private Chain chain;


    public Object invoke() {
        Object result;
        if (chain.incIndex() == chain.getPoints().size()) {
            try {
                //将调用链重置到初始状态
                chain.resetIndex();
                //执行完毕后开始执行原有方法
                result = method.invoke(bean);
            } catch (Exception e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }

        } else {
            // 获取被代理后的方法增强器
            Point point = chain.getPoints().get(chain.getIndex());
            //执行增强其代理
            //在增强器代理中，会回调方法执行器，形成调用链
            result = point.invoke(this);
        }

        return result;
    }
}
