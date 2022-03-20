package com.jungle.demo.shedule.core;

import com.jungle.demo.shedule.common.util.AnnotationUtils;
import com.jungle.demo.shedule.model.ScheduledSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static com.jungle.demo.shedule.common.util.AnnotationUtils.*;

@Slf4j
@DependsOn("scheduledConfig")
@Component
public class MyScheduledPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //1. 获取配置管理器
        MyScheduledConfig scheduledConfig = applicationContext.getBean(MyScheduledConfig.class);

        //2. 获得当前实例化完成的bean的所有方法
        Method[] methods = bean.getClass().getDeclaredMethods();
        //3. 开始检查所有的方法
        if (methods.length > 0) {
            for (Method method : methods) {
                //4.尝试获取scheduled注解
                Scheduled scheduledAnnotation = method.getAnnotation(Scheduled.class);

                if (scheduledAnnotation == null) {
                    continue;
                }
                //5. 创建定时任务元数据，并保存到配置管理器中
                ScheduledSource scheduledSource = new ScheduledSource(scheduledAnnotation);

                //TODO: 检查元数据
                if (!scheduledSource.check()) {
                    throw new RuntimeException("Make a bad schedule in the bean:" + beanName);
                }

                String name = beanName + "." + method.getName();
                scheduledConfig.addScheduleSource(name, scheduledSource);

                //6.取消掉原本的定时任务
                try {
                    cleanOriginalScheduled(scheduledAnnotation);
                } catch (Exception e) {
                    throw new RuntimeException("Make a bad schedule in the bean:" + beanName);
                }

            }
        }

        return bean;
    }

    private void cleanOriginalScheduled(Scheduled annotation) {
        changeAnnotationValue(annotation, "corn", Scheduled.CRON_DISABLED);
        changeAnnotationValue(annotation, "fixedDelay", -1L);
        changeAnnotationValue(annotation, "fixedDelayString", "");
        changeAnnotationValue(annotation, "fixedRate", -1L);
        changeAnnotationValue(annotation, "fixedRateString", "");
        changeAnnotationValue(annotation, "initialDelay", -1L);
        changeAnnotationValue(annotation, "initialDelayString", "");
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {


        return bean;
    }
}
