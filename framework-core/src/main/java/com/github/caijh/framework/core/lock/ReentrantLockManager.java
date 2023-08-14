package com.github.caijh.framework.core.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockManager implements LockManager {

    private static final Map<String, ReentrantLock> LOCKS = new ConcurrentHashMap<>();

    static {
        LOCKS.put("Global", new ReentrantLock());
    }

    @Override
    public Lock get() {
        return LOCKS.get("Global");
    }

    @Override
    public Lock get(String key) {
        synchronized (ReentrantLockManager.class) {
            return LOCKS.computeIfAbsent(key, s -> new ReentrantLock());
        }
    }
}
