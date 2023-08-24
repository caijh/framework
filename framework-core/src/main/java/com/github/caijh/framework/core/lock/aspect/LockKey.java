package com.github.caijh.framework.core.lock.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LockKey {
    private final Class<?> clazz;

    private final Method method;

    private final Object[] params;

    public LockKey(Class<?> clazz, Method method, Object[] params) {
        this.clazz = clazz;
        this.method = method;
        this.params = params;
    }

    @Override
    public String toString() {
        return "Lock:" + clazz.getName() + ":" + method.getName() + ":" + Arrays.deepHashCode(params);
    }
}
