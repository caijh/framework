package com.github.caijh.framework.core.lock.aspect;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.lang.NonNull;



public class LockKeyGenerator implements KeyGenerator {

    public static final String PARAMETERS_VARIABLE = "parameters";


    @NonNull
    @Override
    public Object generate(@NonNull Object target, @NonNull Method method, @NonNull Object... params) {
        return generateKey(target.getClass(),method,params);
    }

    private LockKey generateKey(Class<?> clazz, Method method, Object[] params) {
        return new LockKey(clazz, method, params);
    }

}
