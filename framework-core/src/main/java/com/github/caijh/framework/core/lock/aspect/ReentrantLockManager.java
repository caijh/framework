package com.github.caijh.framework.core.lock.aspect;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class ReentrantLockManager implements LockManager {

    private final LoadingCache<String, ReentrantLock> locks = Caffeine.newBuilder()
        .expireAfterAccess(5, TimeUnit.MINUTES).build(k -> new ReentrantLock());

    @Override
    public Lock get() {
        return locks.get("Global");
    }

    @Override
    public Lock get(String key) {
        return locks.get(key);
    }
}
