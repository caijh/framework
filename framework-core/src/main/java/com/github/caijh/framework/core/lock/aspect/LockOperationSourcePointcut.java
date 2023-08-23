package com.github.caijh.framework.core.lock.aspect;

import java.io.Serializable;
import java.lang.reflect.Method;

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

    /**
     * Returns true if the provided method matches the lock operation source, false otherwise.
     *
     * @param method      the method to check for a lock operation
     * @param targetClass the target class of the method
     * @return true if the method matches the lock operation source, false otherwise
     */
    @Override
    public boolean matches(@NonNull Method method, @NonNull Class<?> targetClass) {
        return (lockOperationSource == null || lockOperationSource.getLockOperation(method, targetClass) != null);
    }

    /**
     * Sets the lock operation source.
     *
     * @param lockOperationSource the lock operation source to be set
     */
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
