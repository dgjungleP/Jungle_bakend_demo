package com.jungle.demo.scheduled.core.strategy;

import com.jungle.demo.scheduled.model.ScheduledSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.FixedDelayTask;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

public class FixedDelayStrategy implements ScheduledStrategy {
    @Override
    public ScheduledFuture<?> schedule(ThreadPoolTaskScheduler taskScheduler, ScheduledSource scheduledSource, Runnable runnable) {
        long fixedDelay = scheduledSource.getFixedDelayString() == null ? scheduledSource.getFixedDelay() : Long.parseLong(scheduledSource.getFixedDelayString());
        long initialDelay = scheduledSource.getInitialDelayString() == null ? scheduledSource.getInitialDelay() : Long.parseLong(scheduledSource.getInitialDelayString());
        FixedDelayTask fixedDelayTask = new FixedDelayTask(runnable, fixedDelay, initialDelay);
        Date startTime = new Date(System.currentTimeMillis() + fixedDelayTask.getInitialDelay());
        return taskScheduler.scheduleWithFixedDelay(runnable, startTime, fixedDelayTask.getInterval());
    }
}
