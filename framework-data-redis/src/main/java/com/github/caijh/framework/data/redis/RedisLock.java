package com.github.caijh.framework.data.redis;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

import com.github.caijh.framework.core.DistributedLock;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

public class RedisLock implements DistributedLock {

    private static final String LOCK = "LOCK";
    private final RedissonClient redissonClient;

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
                String address = redisProperties.getUrl();
                if (address == null) {
                    address = "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort();
                }
                config.useSingleServer().setAddress(address)
                      .setClientName(redisProperties.getClientName()).setPassword(redisProperties.getPassword())
                      .setDatabase(redisProperties.getDatabase())
                      .setConnectTimeout((int)Optional.ofNullable(redisProperties.getTimeout()).orElseGet(() -> Duration.ofSeconds(10)).getSeconds() * 1000)
                ;
            }
        }
        redissonClient = Redisson.create(config);
    }

    @Override
    public Lock get() {
        return this.get(LOCK);
    }

    @Override
    public Lock get(String key) {
        return this.getRedissonRedLock(key);
    }

    private RedissonRedLock getRedissonRedLock(String key) {
        RLock lock = redissonClient.getLock(key);
        return new RedissonRedLock(lock);
    }


}
