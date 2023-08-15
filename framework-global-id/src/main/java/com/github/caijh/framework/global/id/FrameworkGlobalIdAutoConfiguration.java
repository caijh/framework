package com.github.caijh.framework.global.id;

import com.github.caijh.framework.core.lock.aspect.LockManager;
import com.github.caijh.framework.data.redis.support.RedisLockManager;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan
public class FrameworkGlobalIdAutoConfiguration {

    @Bean
    public LockManager lockManager(RedissonClient redissonClient) {
        return new RedisLockManager(redissonClient);
    }

}
