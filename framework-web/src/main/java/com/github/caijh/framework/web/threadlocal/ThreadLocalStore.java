package com.github.caijh.framework.web.threadlocal;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalStore {

    private final Map<Object, Object> map = new HashMap<>();

    public void put(Object key, Object value) {
        this.map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key) {
        return ((T) this.map.get(key));
    }

}
