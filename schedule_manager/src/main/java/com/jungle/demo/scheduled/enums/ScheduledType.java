package com.jungle.demo.scheduled.enums;

public enum ScheduledType {
    CRON(1, "cron"),
    FIXED_DELAY(2, "fixedDelay"),
    FIXED_RATE(3, "fixedRate");

    public final int key;
    public final String value;

    ScheduledType(int key, String value) {
        this.value = value;
        this.key = key;
    }
}
