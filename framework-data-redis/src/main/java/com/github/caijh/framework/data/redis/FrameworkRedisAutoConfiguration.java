package com.github.caijh.framework.data.redis;

import java.time.Duration;
import java.util.Optional;

import com.github.caijh.framework.data.redis.serializer.RedisProtobufSerializer;
import com.github.caijh.framework.data.redis.support.Redis;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@AutoConfiguration(after = RedisAutoConfiguration.class)
@EnableConfigurationProperties(RedisProperties.class)
@ComponentScan
public class FrameworkRedisAutoConfiguration {

    @Primary
    @Bean
    @ConditionalOnBean(value = RedisConnectionFactory.class)
    public RedisTemplate<String, Object> stringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new RedisProtobufSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer()); // fix: redis stream object record must StringRedisSerializer.
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(value = RedisConnectionFactory.class)
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    @Bean
    public RedissonClient redisson(RedisProperties redisProperties) {
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
                      .setConnectTimeout((int) Optional.ofNullable(redisProperties.getTimeout()).orElseGet(() -> Duration.ofSeconds(10)).getSeconds() * 1000)
                      .setConnectionMinimumIdleSize(16)
                ;
            }
        }
        return Redisson.create(config);
    }

    @Bean
    public Redis redis(RedisTemplate<String, Object> stringObjectRedisTemplate, RedissonClient redissonClient) {
        return new Redis(stringObjectRedisTemplate, redissonClient);
    }

}
