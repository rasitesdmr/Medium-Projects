package com.rasitesdmr.proxy;

import com.rasitesdmr.Main;
import com.rasitesdmr.annotation.Component;
import com.rasitesdmr.handler.TransactionInvocationHandler;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ProxyFactory {

    private final List<CustomProxy> beanRegister = new ArrayList<>();

    public ProxyFactory(Package packageToLookup) {
        Reflections reflections = new Reflections(packageToLookup.getName());
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Component.class);
        Set<Class<?>> filterAnnotatedClasses = annotatedClasses.stream().filter(aClass -> !aClass.isAnnotation() && !aClass.isInterface()).collect(Collectors.toSet());
        instantiateBeans(filterAnnotatedClasses);
    }


    private void instantiateBeans(Set<Class<?>> annotatedClasses) {
        List<Object> instanceWithNoArgs = createInstanceWithNoArgs(annotatedClasses);
        this.beanRegister.addAll(createProxies(instanceWithNoArgs));
        List<Object> instanceWithArg = createInstanceWithArg(annotatedClasses);
        this.beanRegister.addAll(createProxies(instanceWithArg));
    }


    private List<Object> createInstanceWithNoArgs(Set<Class<?>> annotatedClasses) {
        List<Object> instances = new ArrayList<>();
        for (Class<?> clazz : annotatedClasses) {
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                if (constructor.getParameterCount() == 0) {
                    try {
                        Object instance = constructor.newInstance();
                        instances.add(instance);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instances;
    }

    private List<Object> createInstanceWithArg(Set<Class<?>> annotatedClasses) {
        List<Object> instances = new ArrayList<>();
        for (Class<?> clazz : annotatedClasses) {
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                if (constructor.getParameterCount() > 0) {
                    try {
                        Object[] dependencies = Arrays.stream(constructor.getParameterTypes())
                                .map(this::getDependencyInstance)
                                .toArray();

                        Object instance = constructor.newInstance(dependencies);
                        instances.add(instance);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instances;
    }


    private Object getDependencyInstance(Class<?> dependencyType) {
        return beanRegister.stream()
                .map(CustomProxy::getTargetInstance)
                .filter(instance -> dependencyType.isAssignableFrom(instance.getClass()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No instance found for dependency: " + dependencyType));
    }


    private List<CustomProxy> createProxies(List<?> beans) {
        return beans.stream()
                .map(this::createProxy)
                .collect(Collectors.toList());
    }


    private CustomProxy createProxy(Object bean) {
        InvocationHandler handler = new TransactionInvocationHandler(bean);
        Object proxyObj = Proxy.newProxyInstance(Main.class.getClassLoader(), bean.getClass().getInterfaces(), handler);
        return new CustomProxy(Arrays.asList(bean.getClass().getInterfaces()), proxyObj);
    }


    public <T> T getBean(Class<T> clazz) {
        Object proxy = beanRegister.stream()
                .filter(p -> p.hasInterface(clazz))
                .findFirst()
                .map(CustomProxy::getJdkProxy)
                .orElseThrow(() -> new RuntimeException("No Bean found for class " + clazz));

        return clazz.cast(proxy);
    }

}
