package com.github.caijh.framework.web.threadlocal;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.NamedThreadLocal;

public class ThreadLocalStore {

    private ThreadLocalStore() {

    }

    private static final ThreadLocal<Map<Object, Object>> THREAD_LOCAL_STORE = new NamedThreadLocal<Map<Object, Object>>("thread local store") {
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<>();
        }
    };

    public static void put(Object key, Object value) {
        THREAD_LOCAL_STORE.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Object key) {
        Object result = THREAD_LOCAL_STORE.get().get(key);

        if (result == null) {
            return null;
        }
        return ((T) result);
    }

    public static void reset() {
        THREAD_LOCAL_STORE.remove();
    }

    public void remove(Object key) {
        THREAD_LOCAL_STORE.get().remove(key);
    }

}
