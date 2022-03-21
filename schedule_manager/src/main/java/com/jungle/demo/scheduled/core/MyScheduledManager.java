package com.jungle.demo.scheduled.core;

import com.jungle.demo.scheduled.model.ScheduledSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MyScheduledManager {

    @Autowired
    private MyScheduledConfig scheduledConfig;


    public void setScheduledCron(String name, String cron) {
        cancelScheduled(name);

        ScheduledSource scheduledSource = scheduledConfig.getNameToScheduleSource().get(name);

        scheduledSource.clear();
        scheduledSource.setCron(cron);

        addSchedule(name, scheduledSource);

    }

    public void setScheduledFixedDelay(String name, Long fixedDelay) {
        cancelScheduled(name);

        ScheduledSource scheduledSource = scheduledConfig.getNameToScheduleSource().get(name);

        scheduledSource.clear();
        scheduledSource.setFixedDelay(fixedDelay);

        addSchedule(name, scheduledSource);

    }

    public void setScheduledFixedRate(String name, Long fixedRate) {
        cancelScheduled(name);

        ScheduledSource scheduledSource = scheduledConfig.getNameToScheduleSource().get(name);

        scheduledSource.clear();
        scheduledSource.setFixedRate(fixedRate);

        addSchedule(name, scheduledSource);

    }

    public List<String> getRunScheduledName() {
        return new ArrayList<>(scheduledConfig.getNameToScheduleFuture().keySet());
    }

    public List<String> getAllScheduledName() {
        return new ArrayList<>(scheduledConfig.getNameToRunnable().keySet());
    }

    private void addSchedule(String name, ScheduledSource scheduledSource) {
        if (getRunScheduledName().contains(name)) {
            throw new RuntimeException("The job:" + name + " has bean run");
        }
        if (!scheduledSource.check()) {
            throw new RuntimeException("The job:" + name + " is a bad job!");
        }
        scheduledSource.refreshType();
        Runnable runnable = scheduledConfig.getNameToRunnable().get(name);
        ThreadPoolTaskScheduler taskScheduler = scheduledConfig.getTaskScheduler();

        ScheduledFuture<?> scheduledFuture = ScheduledFutureFactory.create(taskScheduler, scheduledSource, runnable);
        scheduledConfig.addScheduleSource(name, scheduledSource);
        scheduledConfig.addScheduleFuture(name, scheduledFuture);
    }

    private void cancelScheduled(String name) {

        Map<String, ScheduledFuture<?>> nameToScheduleFuture = scheduledConfig.getNameToScheduleFuture();
        ScheduledFuture<?> future = nameToScheduleFuture.get(name);

        future.cancel(true);
        nameToScheduleFuture.remove(name);
        log.info("Job:" + name + " is closed!");
    }

    public void addCronScheduled(String name, String cron) {
        ScheduledSource scheduledSource = scheduledConfig.getNameToScheduleSource().get(name);
        scheduledSource.setCron(cron);
        addSchedule(name, scheduledSource);

    }

    public void addFixedDelayScheduled(String name, Long fixedDelay, Long... initialDelay) {
        ScheduledSource scheduledSource = scheduledConfig.getNameToScheduleSource().get(name);
        scheduledSource.setFixedDelay(fixedDelay);
        if (initialDelay != null && initialDelay.length == 1) {
            scheduledSource.setInitialDelay(initialDelay[0]);
        } else if (initialDelay != null && initialDelay.length > 1) {
            throw new RuntimeException("Sorry you give a bad initialDelay");
        }
        addSchedule(name, scheduledSource);

    }

    public void addFixedRateScheduled(String name, Long fixedRate, Long... initialDelay) {
        ScheduledSource scheduledSource = scheduledConfig.getNameToScheduleSource().get(name);
        scheduledSource.setFixedRate(fixedRate);
        if (initialDelay != null && initialDelay.length == 1) {
            scheduledSource.setInitialDelay(initialDelay[0]);
        } else if (initialDelay != null && initialDelay.length > 1) {
            throw new RuntimeException("Sorry you give a bad initialDelay");
        }
        addSchedule(name, scheduledSource);

    }


    public void runScheduled(String name) {
        Runnable runnable = scheduledConfig.getNameToRunnable().get(name);
        runnable.run();
    }


}
