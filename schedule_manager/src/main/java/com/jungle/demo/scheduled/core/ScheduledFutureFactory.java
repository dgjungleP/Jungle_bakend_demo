package com.jungle.demo.scheduled.core;

import com.jungle.demo.scheduled.model.ScheduledSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ScheduledFuture;

public class ScheduledFutureFactory {
    public static ScheduledFuture<?> create(ThreadPoolTaskScheduler taskScheduler, ScheduledSource scheduledSource, Runnable runnable) {
        return null;
    }
}
