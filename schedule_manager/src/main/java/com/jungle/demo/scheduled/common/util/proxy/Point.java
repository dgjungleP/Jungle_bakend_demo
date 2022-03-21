package com.jungle.demo.scheduled.common.util.proxy;

import com.jungle.demo.scheduled.core.intercepter.MyScheduledRunnable;
import lombok.Data;

@Data
public abstract class Point {

    private String myScheduledName;

    public abstract Object invoke(MyScheduledRunnable runnable);
}
