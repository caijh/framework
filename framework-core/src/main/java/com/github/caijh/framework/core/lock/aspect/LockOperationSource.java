package com.github.caijh.framework.core.lock.aspect;

import java.lang.reflect.Method;

import com.github.caijh.framework.core.lock.config.LockAnnotationParser;
import org.springframework.lang.Nullable;

public interface LockOperationSource extends LockAnnotationParser {
    @Nullable
    LockOperation getLockOperation(Method method, @Nullable Class<?> targetClass);

}
