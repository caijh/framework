package com.github.caijh.framework.redis.autoconfigure;

import com.github.caijh.framework.redis.Redis;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@AutoConfigureAfter(value = {RedisAutoConfiguration.class})
public class RedisAutoConfig {

    @Bean
    public RedisTemplate<String, Object> stringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setEnableTransactionSupport(true);
        return template;
    }


    @Bean
    public Redis redis(RedisTemplate<String, Object> stringObjectRedisTemplate) {
        return new Redis(stringObjectRedisTemplate);
    }

}
