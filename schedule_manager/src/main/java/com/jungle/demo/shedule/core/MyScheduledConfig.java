package com.jungle.demo.shedule.core;

import com.jungle.demo.shedule.model.ScheduledSource;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component("scheduledConfig")
@Data
public class MyScheduledConfig {

    /**
     * 执行定时任务的线程池
     */
    private ThreadPoolTaskScheduler taskScheduler;

    /**
     * 定时任务名称域定时任务的回调函数的关系容器
     * TODO: 什么是定时任务的回调
     */
    private Map<String, ScheduledFuture> nameToScheduleFuture = new ConcurrentHashMap<>();

    /**
     * 定时任务的名称和定时任务需要执行的逻辑的关系容器
     * TODO: 和回调函数之间的区别是什么
     */
    private Map<String, Runnable> nameToRunnable = new ConcurrentHashMap<>();

    /**
     * 定时任务名称和定时任务的源信息的关系容器
     */
    private Map<String, ScheduledSource> nameToScheduleSource = new ConcurrentHashMap<>();

    public void addScheduleSource(String name, ScheduledSource scheduledSource) {

    }
}
