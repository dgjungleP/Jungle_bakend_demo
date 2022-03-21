package com.jungle.demo.scheduled.core;

import com.jungle.demo.scheduled.common.util.proxy.Chain;
import com.jungle.demo.scheduled.common.util.proxy.Point;
import com.jungle.demo.scheduled.common.util.proxy.ProxyUtils;
import com.jungle.demo.scheduled.core.intercepter.MyScheduledRunnable;
import com.jungle.demo.scheduled.core.intercepter.RunnableBaseInterceptor;
import com.jungle.demo.scheduled.core.intercepter.strengthen.BaseStrengthen;
import com.jungle.demo.scheduled.model.ScheduledSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
@DependsOn("threadPoolTaskScheduler")
public class MyScheduleApplicationRunner implements ApplicationRunner, ApplicationContextAware {

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
    private ApplicationContext applicationContext;


    @Autowired
    private MyScheduledConfig scheduledConfig;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //1. 设置定时任务执行线程池
        scheduledConfig.setTaskScheduler(taskScheduler);

        //2. 获取定时任务元数据
        Map<String, ScheduledSource> scheduleSourceMap = scheduledConfig.getNameToScheduleSource();

        for (String scheduleName : scheduleSourceMap.keySet()) {
            ScheduledSource scheduledSource = scheduleSourceMap.get(scheduleName);
            //3.获取素有增强类
            String[] strengthenNames = applicationContext.getBeanNamesForType(BaseStrengthen.class);

            //4.创建执行控制器
            MyScheduledRunnable runnable = new MyScheduledRunnable();
            //配置执行器
            runnable.setMethod(scheduledSource.getMethod());
            runnable.setBean(scheduledSource.getBean());
            //5.逐一处理增强类
            List<Point> points = new ArrayList<>(strengthenNames.length);
            for (String strengthenName : strengthenNames) {
                //6. 将增强器戴利城point TODO:为什么要代理为point
                Object bean = applicationContext.getBean(strengthenName);
                Point proxy = ProxyUtils.getInstance(Point.class, new RunnableBaseInterceptor(bean, runnable));
                proxy.setMyScheduledName(scheduleName);
                points.add(proxy);
            }
            //7.将增强代理组成调用链
            runnable.setChain(new Chain(points));
            scheduledConfig.addRunnable(scheduleName, runnable::invoke);
            try {
                ScheduledFuture<?> scheduledFuture = ScheduledFutureFactory.create(taskScheduler, scheduledSource, runnable::invoke);
                scheduledConfig.addScheduleFuture(scheduleName, scheduledFuture);
                log.info("The schedule job:" + scheduleName + " start success!");
            } catch (Exception e) {
                throw new RuntimeException("The schedule job:" + scheduleName + " start failed");
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
