package com.jungle.demo.scheduled.demo;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class ScheduleTestDemo {

    @Scheduled(cron = "0/5 * * * * ?")
    public void printOne() {
        System.out.println(1);
    }
}
