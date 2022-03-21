package com.jungle.demo.scheduled.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Objects;

public class AnnotationUtils {
    public static void changeAnnotationValue(Annotation annotation, String key, Object newValue) throws Exception {
        InvocationHandler handler = Proxy.getInvocationHandler(annotation);
        Field field;
        try {
            //注解的成员变量，并不会直接存储在class对象身上，而是在一个容器里面
            field = handler.getClass().getDeclaredField("memberValues");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

        field.setAccessible(true);
        Map<String, Object> values = (Map<String, Object>) field.get(handler);
        Object oldValue = values.get(key);
        if (oldValue == null || !Objects.equals(oldValue.getClass(), newValue.getClass())) {
            throw new IllegalArgumentException();
        }
        values.put(key, newValue);
    }
}
