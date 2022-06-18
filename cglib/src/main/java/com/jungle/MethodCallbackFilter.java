package com.jungle;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class MethodCallbackFilter implements CallbackFilter {

    @Override
    public int accept(Method method) {
        String name = method.getName();
        if (name.equals("eat")) {
            return 1;
        }
        return 0;
    }
}
