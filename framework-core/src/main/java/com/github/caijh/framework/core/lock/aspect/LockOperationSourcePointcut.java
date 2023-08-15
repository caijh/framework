package com.github.caijh.framework.core.lock.aspect;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.github.caijh.framework.core.lock.LockOperationSource;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class LockOperationSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {

    @Nullable
    private LockOperationSource lockOperationSource;

    public LockOperationSourcePointcut() {
        setClassFilter(new LockOperationSourceClassFilter());
    }

    @Override
    public boolean matches(@NonNull Method method, @NonNull Class<?> targetClass) {
        return (lockOperationSource == null || lockOperationSource.getLockOperation(method, targetClass) != null);
    }

    public void setLockOperationSource(@Nullable LockOperationSource lockOperationSource) {
        this.lockOperationSource = lockOperationSource;
    }

    private class LockOperationSourceClassFilter implements ClassFilter {

        @Override
        public boolean matches(@NonNull Class<?> clazz) {
            return (lockOperationSource == null || lockOperationSource.isCandidateClass(clazz));
        }

    }
}
