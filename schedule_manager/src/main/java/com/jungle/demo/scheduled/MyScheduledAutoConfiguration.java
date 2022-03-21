package com.jungle.demo.scheduled;

import com.jungle.demo.scheduled.core.intercepter.strengthen.SimpleStrengthen;
import com.jungle.demo.scheduled.properties.ThreadPoolTaskSchedulerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan("com.jungle.demo.scheduled")
@EnableConfigurationProperties(ThreadPoolTaskSchedulerProperties.class)
public class MyScheduledAutoConfiguration {

    @Autowired
    private ThreadPoolTaskSchedulerProperties threadPoolTaskSchedulerProperties;

    @Bean("threadPoolTaskScheduler")
    @ConditionalOnMissingBean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(threadPoolTaskSchedulerProperties.getPoolSize());
        taskScheduler.setThreadNamePrefix(threadPoolTaskSchedulerProperties.getThreadNamePrefix());
        taskScheduler.setWaitForTasksToCompleteOnShutdown(threadPoolTaskSchedulerProperties.getWaitForTasksToCompleteOnShutdown());
        taskScheduler.setAwaitTerminationSeconds(threadPoolTaskSchedulerProperties.getAwaitTerminationSeconds());
        taskScheduler.initialize();
        return taskScheduler;
    }

    @Bean
    @ConditionalOnMissingBean
    public SimpleStrengthen colonyStrengthen() {
        return new SimpleStrengthen();
    }
}
