package com.jungle.demo.shedule.model;

import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

@Data
public class ScheduledSource {
    /**
     * cron表达式
     */
    private String cron;
    /**
     * 时区，cron表达式会基于该时区解析
     */
    private String zone;
    /**
     * 上一次执行完毕时间点之后多长时间再执行
     */
    private Long fixedDelay;
    /**
     * 与 fixedDelay 意思相同，只是使用字符串的形式
     */
    private String fixedDelayString;
    /**
     * 上一次开始执行时间点之后多长时间再执行
     */
    private Long fixedRate;
    /**
     * 与 fixedRate 意思相同，只是使用字符串的形式
     */
    private String fixedRateString;
    /**
     * 第一次延迟多长时间后再执行
     */
    private Long initialDelay;
    /**
     * 与 initialDelay 意思相同，只是使用字符串的形式
     */
    private String initialDelayString;

    public ScheduledSource(Scheduled annotation) {
        this.cron = StringUtils.hasText(annotation.cron()) ? null : annotation.cron();
        this.fixedDelay = annotation.fixedDelay() < 0 ? null : annotation.fixedDelay();
        this.fixedDelayString = StringUtils.hasText(annotation.fixedDelayString()) ? null : annotation.fixedDelayString();
        this.fixedRate = annotation.fixedRate() < 0 ? null : annotation.fixedRate();
        this.fixedRateString = StringUtils.hasText(annotation.fixedRateString()) ? null : annotation.fixedRateString();
        this.initialDelay = annotation.initialDelay() < 0 ? null : annotation.initialDelay();
        this.initialDelayString = StringUtils.hasText(annotation.initialDelayString()) ? null : annotation.initialDelayString();
    }

    public boolean check() {
        return true;
    }
}
