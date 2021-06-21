package com.github.caijh.framework.data.redis.support;

import java.util.concurrent.locks.Lock;

import com.github.caijh.framework.core.DistributedLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class RedisLock implements DistributedLock {

    private static final String LOCK = "LOCK";
    private final RedissonClient redissonClient;

    public RedisLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Lock get() {
        return this.get(RedisLock.LOCK);
    }

    @Override
    public Lock get(String key) {
        return this.getRedissonRedLock(key);
    }

    private RedissonRedLock getRedissonRedLock(String key) {
        RLock lock = this.redissonClient.getLock(key);
        return new RedissonRedLock(lock);
    }


}
