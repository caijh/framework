package com.github.caijh.framework.data.redis.autoconfigure;

import com.github.caijh.framework.data.redis.Redis;
import com.github.caijh.framework.data.redis.serializer.ProtobufSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@AutoConfigureAfter(value = RedisAutoConfiguration.class)
public class FrameworkRedisAutoConfiguration {

    @Primary
    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<String, Object> stringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new ProtobufSerializer());
        redisTemplate.setHashValueSerializer(new ProtobufSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public Redis redis(RedisTemplate<String, Object> stringObjectRedisTemplate) {
        return new Redis(stringObjectRedisTemplate);
    }

}
