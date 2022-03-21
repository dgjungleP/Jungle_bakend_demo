package com.jungle.dashbord.schedule;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimpleSchedule {

    @Scheduled(cron = "0/5 * * * * ? ")
    public void SimpleSchedule() {
        System.out.println("hello ");
    }
}
