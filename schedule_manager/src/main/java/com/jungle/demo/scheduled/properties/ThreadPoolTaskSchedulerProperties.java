package com.jungle.demo.scheduled.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my-schedule.thread-pool")
@Data
public class ThreadPoolTaskSchedulerProperties {
    private Integer poolSize = 10;
    private String threadNamePrefix;
    private Boolean waitForTasksToCompleteOnShutdown = false;
    private Integer awaitTerminationSeconds = 0;
}
