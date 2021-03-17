package com.github.caijh.framework.cache.autoconfigure;

import java.time.Duration;
import javax.annotation.Nonnull;

import com.github.caijh.framework.cache.RedisWithTtlCacheManager;
import com.github.caijh.framework.cache.exception.CacheConfigException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class FrameworkCacheAutoConfiguration implements EnvironmentAware {

    private Environment env;

    @Bean
    @ConditionalOnBean(value = RedisTemplate.class)
    @ConditionalOnMissingBean
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        String appName = env.getProperty("spring.application.name", "UNKNOWN");

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(redisTemplate.getStringSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(redisTemplate.getValueSerializer()))
                .entryTtl(Duration.ofMinutes(1))
                .computePrefixWith(cacheName -> "APP:" + appName + ":caching:" + cacheName);

        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        if (connectionFactory == null) {
            throw new CacheConfigException("Redis ConnectionFactory is null.");
        }
        return new RedisWithTtlCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory), redisCacheConfiguration);
    }

    @Override
    public void setEnvironment(@Nonnull Environment environment) {
        this.env = environment;
    }

}
