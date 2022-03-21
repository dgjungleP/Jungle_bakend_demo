package com.jungle.demo.scheduled.model;

import com.jungle.demo.scheduled.enums.ScheduledType;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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

    private transient Method method;

    private transient Object bean;

    private ScheduledType type;


    public ScheduledSource(Scheduled annotation, Method method, Object bean) {
        this.cron = ObjectUtils.isEmpty(annotation.cron()) ? null : annotation.cron();
        this.fixedDelay = annotation.fixedDelay() < 0 ? null : annotation.fixedDelay();
        this.fixedDelayString = ObjectUtils.isEmpty(annotation.fixedDelayString()) ? null : annotation.fixedDelayString();
        this.fixedRate = annotation.fixedRate() < 0 ? null : annotation.fixedRate();
        this.fixedRateString = ObjectUtils.isEmpty(annotation.fixedRateString()) ? null : annotation.fixedRateString();
        this.initialDelay = annotation.initialDelay() < 0 ? null : annotation.initialDelay();
        this.initialDelayString = ObjectUtils.isEmpty(annotation.initialDelayString()) ? null : annotation.initialDelayString();
        this.method = method;
        this.bean = bean;
        this.type = confirmType();
    }

    private ScheduledType confirmType() {
        if (cron != null) {
            return ScheduledType.CRON;
        } else if (fixedDelay != null || fixedDelayString != null) {
            return ScheduledType.FIXED_DELAY;
        } else if (fixedRate != null || fixedRateString != null) {
            return ScheduledType.FIXED_RATE;
        }
        return null;
    }

    //TODO: 为什么这样检查是对的
    public boolean check() {
        String sb = "1"
                + (cron == null ? 0 : 1)
                + (fixedDelay == null ? 0 : 1)
                + (fixedDelayString == null ? 0 : 1)
                + (fixedRate == null ? 0 : 1)
                + (fixedRateString == null ? 0 : 1)
                + (initialDelay == null ? 0 : 1)
                + (initialDelayString == null ? 0 : 1);
        Integer flag = Integer.valueOf(sb, 2);
        List<Integer> probability = Arrays.asList(132, 133, 134, 136, 137, 138, 144, 145, 146, 160, 161, 162, 192);
        return probability.contains(flag);
    }

    public void clear() {
        this.cron = null;
        this.fixedDelay = null;
        this.fixedDelayString = null;
        this.fixedRate = null;
        this.fixedRateString = null;
    }

    public void refreshType() {
        this.type = confirmType();
    }
}
