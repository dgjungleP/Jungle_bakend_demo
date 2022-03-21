package com.jungle.demo.scheduled.common.util.proxy;

import com.jungle.demo.scheduled.core.intercepter.MyScheduledRunnable;
import lombok.Data;

@Data
public abstract class Point {

    private String myScheduledName;

    //TODO: 谁来实现了invoke方法 cglib实现了这个方法
    public abstract Object invoke(MyScheduledRunnable runnable);
}
