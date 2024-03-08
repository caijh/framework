package com.github.caijh.framework.core.lock.aspect;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class LockInterceptor implements MethodInterceptor, BeanFactoryAware {

    private final LockOperationExpressionEvaluator evaluator = new LockOperationExpressionEvaluator();
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    private LockKeyGenerator keyGenerator;
    private LockManager lockManager;
    private LockOperationSource lockOperationSource;
    @Nullable
    private BeanFactory beanFactory;

    @Override
    public Object invoke(@NonNull MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object target = invocation.getThis();
        Assert.state(target != null, "target must not be null");

        if (lockOperationSource != null) {
            Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
            LockOperation lockOperation = lockOperationSource.getLockOperation(method, targetClass);
            if (lockOperation != null) {
                String key = lockOperation.getKey();
                if (StringUtils.hasText(key)) {
                    LockOperationMetadata lockOperationMetadata = new LockOperationMetadata(lockOperation, method, targetClass);
                    EvaluationContext evaluationContext = createEvaluationContext(method,
                        invocation.getArguments(), target, targetClass, lockOperationMetadata.getTargetMethod(), beanFactory);
                    key = "Lock:" + Objects.requireNonNull(evaluator.key(key, lockOperationMetadata.getMethodKey(), evaluationContext)).toString();
                } else {
                    key = keyGenerator.generate(target, method, invocation.getArguments()).toString();
                }
                LockManager manager = Optional.ofNullable(lockManager).orElse(new ReentrantLockManager());
                Lock lock = manager.get(key);
                if (lockOperation.getExpired() != -1) {
                    return execute(invocation, lock, lockOperation.getExpired());
                } else {
                    return execute(invocation, lock);
                }
            }
        }

        return invocation.proceed();
    }

    private Object execute(MethodInvocation invocation, Lock lock) throws Throwable {
        lock.lock();
        try {
            return invocation.proceed();
        } finally {
            lock.unlock();
        }
    }

    private Object execute(MethodInvocation invocation, Lock lock, int time) throws Throwable {
        boolean success = lock.tryLock(time, TimeUnit.MILLISECONDS);
        Assert.isTrue(success, "try lock failure");
        try {
            return invocation.proceed();
        } finally {
            lock.unlock();
        }
    }

    public void configure(LockOperationSource lockOperationSource, LockKeyGenerator keyGenerator, LockManager lockManager) {
        this.keyGenerator = keyGenerator;
        this.lockManager = lockManager;
        this.lockOperationSource = lockOperationSource;
    }

    public EvaluationContext createEvaluationContext(Method method, Object[] args, Object target, Class<?> targetClass, Method targetMethod,
                                                     @Nullable BeanFactory beanFactory) {

        LockExpressionRootObject rootObject = new LockExpressionRootObject(method, args, target, targetClass);
        LockEvaluationContext evaluationContext = new LockEvaluationContext(
                rootObject, targetMethod, args, getParameterNameDiscoverer());

        if (beanFactory != null) {
            evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
        }
        return evaluationContext;
    }

    private ParameterNameDiscoverer getParameterNameDiscoverer() {
        return parameterNameDiscoverer;
    }

    @Override
    public void setBeanFactory(@Nullable BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}