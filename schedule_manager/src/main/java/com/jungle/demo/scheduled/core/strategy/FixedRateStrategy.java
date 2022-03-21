package com.jungle.demo.scheduled.core.strategy;

import com.jungle.demo.scheduled.model.ScheduledSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.FixedRateTask;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

public class FixedRateStrategy implements ScheduledStrategy {
    @Override
    public ScheduledFuture<?> schedule(ThreadPoolTaskScheduler taskScheduler, ScheduledSource scheduledSource, Runnable runnable) {
        long fixedRate = scheduledSource.getFixedRateString() == null ? scheduledSource.getFixedRate() : Long.parseLong(scheduledSource.getFixedRateString());
        long initialDelay = scheduledSource.getInitialDelayString() == null ? scheduledSource.getInitialDelay() : Long.parseLong(scheduledSource.getInitialDelayString());
        FixedRateTask fixedRateTask = new FixedRateTask(runnable, fixedRate, initialDelay);
        Date startTime = new Date(System.currentTimeMillis() + fixedRateTask.getInitialDelay());
        return taskScheduler.scheduleWithFixedDelay(runnable, startTime, fixedRateTask.getInterval());
    }
}
