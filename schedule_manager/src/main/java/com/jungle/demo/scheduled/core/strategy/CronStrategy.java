package com.jungle.demo.scheduled.core.strategy;

import com.jungle.demo.scheduled.model.ScheduledSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ScheduledFuture;

public class CronStrategy implements ScheduledStrategy {
    @Override
    public ScheduledFuture<?> schedule(ThreadPoolTaskScheduler taskScheduler, ScheduledSource scheduledSource, Runnable runnable) {
        return taskScheduler.schedule(runnable, new CronTrigger(scheduledSource.getCron()));
    }
}
