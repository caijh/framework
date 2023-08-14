package com.github.caijh.framework.data.redis.support;

import java.util.concurrent.locks.Lock;

import com.github.caijh.framework.core.lock.LockManager;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.util.StringUtils;

public class RedisLockManager implements LockManager {

    private static final String LOCK = "LOCK";
    private final RedissonClient redissonClient;

    public RedisLockManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Lock get() {
        return this.get(RedisLockManager.LOCK);
    }

    @Override
    public Lock get(String key) {
        if (!StringUtils.hasText(key)) {
            key = LOCK;
        }
        RLock lock = this.redissonClient.getLock(key);
        return new RedissonRedLock(lock);
    }

}
