package com.github.caijh.framework.core.lock;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import lombok.Getter;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.BridgeMethodResolver;

@Getter
public class LockOperationMetadata {
    private final LockOperation operation;

    private final Method method;

    private final Class<?> targetClass;

    private final Method targetMethod;

    private final AnnotatedElementKey methodKey;

    public LockOperationMetadata(LockOperation operation, Method method, Class<?> targetClass) {
        this.operation = operation;
        this.method = BridgeMethodResolver.findBridgedMethod(method);
        this.targetClass = targetClass;
        this.targetMethod = (!Proxy.isProxyClass(targetClass) ?
            AopUtils.getMostSpecificMethod(method, targetClass) : this.method);
        this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);
    }
}
