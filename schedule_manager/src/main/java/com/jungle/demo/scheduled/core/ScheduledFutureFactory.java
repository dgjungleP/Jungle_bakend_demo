package com.jungle.demo.scheduled.core;

import com.jungle.demo.scheduled.core.strategy.CronStrategy;
import com.jungle.demo.scheduled.core.strategy.FixedDelayStrategy;
import com.jungle.demo.scheduled.core.strategy.FixedRateStrategy;
import com.jungle.demo.scheduled.core.strategy.ScheduledStrategy;
import com.jungle.demo.scheduled.enums.ScheduledType;
import com.jungle.demo.scheduled.model.ScheduledSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class ScheduledFutureFactory {

    private static Map<ScheduledType, ScheduledStrategy> scheduledStrategyCache = new HashMap<>(3);


    public static ScheduledFuture<?> create(ThreadPoolTaskScheduler taskScheduler, ScheduledSource scheduledSource, Runnable runnable) {
        ScheduledType type = scheduledSource.getType();
        ScheduledStrategy scheduledStrategy = scheduledStrategy(type);
        return scheduledStrategy.schedule(taskScheduler, scheduledSource, runnable);
    }

    private static ScheduledStrategy scheduledStrategy(ScheduledType type) {
        ScheduledStrategy strategy = scheduledStrategyCache.get(type);
        if (strategy != null) {
            return strategy;
        }
        switch (type) {
            case CRON:
                strategy = new CronStrategy();
                break;
            case FIXED_DELAY:
                strategy = new FixedDelayStrategy();
                break;
            case FIXED_RATE:
                strategy = new FixedRateStrategy();
                break;
            default:
                throw new RuntimeException("Sorry not support this type:" + type);
        }
        scheduledStrategyCache.put(type, strategy);
        return strategy;
    }
}
