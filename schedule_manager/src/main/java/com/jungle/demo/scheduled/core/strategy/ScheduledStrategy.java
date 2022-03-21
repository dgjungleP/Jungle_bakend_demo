package com.jungle.demo.scheduled.core.strategy;

import com.jungle.demo.scheduled.model.ScheduledSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ScheduledFuture;

public interface ScheduledStrategy {
    ScheduledFuture<?> schedule(ThreadPoolTaskScheduler taskScheduler, ScheduledSource scheduledSource, Runnable runnable);
}
