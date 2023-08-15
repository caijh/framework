package com.github.caijh.framework.core.lock.config;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.github.caijh.framework.core.lock.annotation.Locking;
import com.github.caijh.framework.core.lock.aspect.LockOperation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;

public interface LockAnnotationParser extends Serializable {
    default boolean isCandidateClass(Class<?> targetClass) {
        return AnnotationUtils.isCandidateClass(targetClass, Locking.class);
    }

    @Nullable
    default LockOperation parseLockAnnotation(Method method) {
        Locking mergedAnnotation = AnnotatedElementUtils.getMergedAnnotation(method, Locking.class);
        if (mergedAnnotation != null) {
            return new LockOperation(mergedAnnotation.key(), mergedAnnotation.expired());
        }

        return null;
    }
}
