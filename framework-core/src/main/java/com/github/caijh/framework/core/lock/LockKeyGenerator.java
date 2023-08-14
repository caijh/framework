package com.github.caijh.framework.core.lock;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.lang.NonNull;


public class LockKeyGenerator implements KeyGenerator {

    public static final String PARAMETERS_VARIABLE = "parameters";


    @NonNull
    @Override
    public Object generate(@NonNull Object target, @NonNull Method method, @NonNull Object... params) {
        return SimpleKeyGenerator.generateKey(params);
    }


}
