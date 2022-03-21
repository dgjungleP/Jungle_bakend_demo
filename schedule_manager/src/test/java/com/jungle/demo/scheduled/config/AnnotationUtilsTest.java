package com.jungle.demo.scheduled.config;

import com.jungle.demo.scheduled.common.util.AnnotationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootApplication
class AnnotationUtilsTest {

    @Test
    @Scheduled(cron = "0/5 * * * * ? ")
    public void setAnnotationValue() throws Exception {
        Scheduled annotationValue = this.getClass().getMethod("setAnnotationValue").getAnnotation(Scheduled.class);
        AnnotationUtils.changeAnnotationValue(annotationValue, "cron", Scheduled.CRON_DISABLED);

    }
}