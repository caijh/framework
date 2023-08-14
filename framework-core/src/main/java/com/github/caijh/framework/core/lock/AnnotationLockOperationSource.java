package com.github.caijh.framework.core.lock;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodClassKey;
import org.springframework.lang.Nullable;

public class AnnotationLockOperationSource implements LockOperationSource {

    private final Map<Object, LockOperation> attributeCache = new ConcurrentHashMap<>(1024);

    private final LockAnnotationParser annotationParser = new LockAnnotationParser() {
    };


    @Override
    public boolean isCandidateClass(Class<?> targetClass) {
        return annotationParser.isCandidateClass(targetClass);
    }

    @Nullable
    @Override
    public LockOperation getLockOperation(Method method, @Nullable Class<?> targetClass) {
        if (method.getDeclaringClass() == Object.class) {
            return null;
        }

        MethodClassKey methodClassKey = new MethodClassKey(method, targetClass);
        return attributeCache.computeIfAbsent(methodClassKey, o -> computeLockOperation(method, targetClass));
    }

    private LockOperation computeLockOperation(Method method, Class<?> targetClass) {
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        LockOperation lockOperation = annotationParser.parseLockAnnotation(specificMethod);
        if (lockOperation != null) {
            return lockOperation;
        }

        if (specificMethod != method) {
            lockOperation = annotationParser.parseLockAnnotation(method);
            return lockOperation;
        }

        return null;
    }
}
