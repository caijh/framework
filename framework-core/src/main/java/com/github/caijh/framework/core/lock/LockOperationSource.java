package com.github.caijh.framework.core.lock;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.springframework.lang.Nullable;

public interface LockOperationSource extends Serializable {
    default boolean isCandidateClass(Class<?> targetClass) {
        return true;
    }

    @Nullable
    LockOperation getLockOperation(Method method, @Nullable Class<?> targetClass);

}
