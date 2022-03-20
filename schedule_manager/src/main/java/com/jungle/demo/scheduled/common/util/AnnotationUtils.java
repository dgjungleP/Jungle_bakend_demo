package com.jungle.demo.scheduled.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class AnnotationUtils {
    public static void changeAnnotationValue(Annotation annotation, String key, Object newValue) throws Exception {
        InvocationHandler handler = Proxy.getInvocationHandler(annotation);
        Field field;
        try {
            field = handler.getClass().getDeclaredField("memberValues");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

    }
}
