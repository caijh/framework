package com.github.caijh.framework.core.lock;

import java.lang.reflect.Method;

import org.springframework.lang.Nullable;

public interface LockOperationSource extends LockAnnotationParser {
    @Nullable
    LockOperation getLockOperation(Method method, @Nullable Class<?> targetClass);

}
