package com.github.caijh.framework.core.threadlocal;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.NamedThreadLocal;

public class ThreadLocalStore {

    private static final ThreadLocal<Map<Object, Object>> STORE = new NamedThreadLocal<Map<Object, Object>>("thread local store") {
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<>();
        }
    };

    private ThreadLocalStore() {}

    public static void put(Object key, Object value) {
        STORE.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Object key) {
        Object result = STORE.get().get(key);

        if (result == null) {
            return null;
        }
        return ((T) result);
    }

    public static void reset() {
        STORE.remove();
    }

    public void remove(Object key) {
        STORE.get().remove(key);
    }

}
