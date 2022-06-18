package com.jungle;

import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Method;

public class MyCallbackHelper extends CallbackHelper {
    public MyCallbackHelper(Class superclass, Class[] interfaces) {
        super(superclass, interfaces);
    }

    @Override
    protected Object getCallback(Method method) {

        if (method.getName().equals("readBook")) {
            return new ProxyInterceptor();
        }
        return NoOp.INSTANCE;
    }
}
