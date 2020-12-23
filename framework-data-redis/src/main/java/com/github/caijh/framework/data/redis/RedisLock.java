package com.github.caijh.framework.data.redis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.github.caijh.framework.core.DistributedLock;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

public class RedisLock implements DistributedLock {

    public static final String LOCK = "LOCK";
    private final RedissonClient redissonClient;
    private final Map<String, RedissonRedLock> locks = new ConcurrentHashMap<>();

    public RedisLock(RedisProperties redisProperties) {
        Config config = new Config();
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        if (cluster != null) {
            config.useClusterServers().addNodeAddress(cluster.getNodes().toArray(new String[]{}))
                  .setClientName(redisProperties.getClientName())
                  .setPassword(redisProperties.getPassword())
                  .setRetryAttempts(cluster.getMaxRedirects()).setConnectTimeout((int) (redisProperties.getTimeout().getSeconds() * 1000))
                  .setScanInterval(5000);
        } else {
            RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
            if (sentinel != null) {
                config.useSentinelServers().addSentinelAddress(sentinel.getNodes().toArray(new String[]{}))
                      .setClientName(redisProperties.getClientName()).setPassword(redisProperties.getPassword())
                      .setDatabase(redisProperties.getDatabase()).setMasterName(sentinel.getMaster()).setScanInterval(5000)
                      .setConnectTimeout((int) (redisProperties.getTimeout().getSeconds() * 1000));
            } else {
                config.useSingleServer().setAddress(redisProperties.getHost() + ":" + redisProperties.getPort())
                      .setClientName(redisProperties.getClientName()).setPassword(redisProperties.getPassword())
                      .setDatabase(redisProperties.getDatabase()).setConnectTimeout((int) (redisProperties.getTimeout().getSeconds() * 1000));
            }
        }
        redissonClient = Redisson.create(config);
    }

    @Override
    public void acquire() throws Exception {
        this.acquire(LOCK, -1, null);
    }

    @Override
    public boolean acquire(long time, TimeUnit timeUnit) throws Exception {
        return this.acquire(LOCK, time, timeUnit);
    }

    @Override
    public boolean acquire(String key, long time, TimeUnit timeUnit) throws Exception {
        return this.getRedissonRedLock(key).tryLock(time, timeUnit);
    }

    @Override
    public void release() {
        this.release(LOCK);
    }

    @Override
    public void release(String key) {
        this.getRedissonRedLock(key).unlock();
    }

    private RedissonRedLock getRedissonRedLock(String key) {
        return locks.computeIfAbsent(key, s -> {
            RLock lock = redissonClient.getLock(key);
            return new RedissonRedLock(lock);
        });
    }

}
