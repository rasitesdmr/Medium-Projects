package com.rasitesdmr.proxy;

import com.rasitesdmr.handler.TransactionInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;


public class CustomProxy {


    private List<Class<?>> interfaces;

    private Object proxy;

    public CustomProxy(List<Class<?>> interfaces, Object proxy) {
        this.interfaces = interfaces;
        this.proxy = proxy;
    }

    public Object getJdkProxy() {
        return proxy;
    }

    public boolean hasInterface(Class<?> expectedInterface) {
        return interfaces.contains(expectedInterface);
    }

    public Object getTargetInstance() {
        if (proxy instanceof Proxy) {
            InvocationHandler handler = Proxy.getInvocationHandler(proxy);
            if (handler instanceof TransactionInvocationHandler) {
                return ((TransactionInvocationHandler) handler).getTarget();
            }
        }
        return proxy;
    }

}
