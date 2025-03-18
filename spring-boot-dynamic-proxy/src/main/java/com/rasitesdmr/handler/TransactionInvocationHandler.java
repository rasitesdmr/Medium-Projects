package com.rasitesdmr.handler;

import com.rasitesdmr.annotation.Transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class TransactionInvocationHandler implements InvocationHandler {

    private final Object target;
    private final Class<?> targetClass;


    public TransactionInvocationHandler(Object targetObject) {
        this.target = targetObject;
        this.targetClass = targetObject.getClass();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method targetMethod = getOverriddenMethod(method);

        return getTransactionalMethod(targetMethod)
                .map(annotation -> handleTransactionalMethod(method, args, annotation))
                .orElseGet(() -> uncheckedInvoke(method, args));

    }


    private Object handleTransactionalMethod(Method method, Object[] args, Transaction annotation) {
        Object result;
        System.out.printf("Opening transaction [%s] with params %s%n", annotation.value(), Arrays.toString(args));

        try {
            result = uncheckedInvoke(method, args);
        } catch (RuntimeException e) {
            System.out.printf("Rollback transaction transaction %s...%n", annotation.value());
            throw e;
        }

        System.out.printf("Committing transaction %s...%n", annotation.value());

        return result;
    }

    private Optional<Transaction> getTransactionalMethod(Method method) {
        return Optional.ofNullable(method.getDeclaredAnnotation(Transaction.class));
    }


    private Object uncheckedInvoke(Method method, Object[] args) throws RuntimeException {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Could not invoke method " + method.getName(), e);
        }
    }


    private Method getOverriddenMethod(Method method) throws NoSuchMethodException {
        return targetClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
    }

    public Object getTarget() {
        return target;
    }
}
