package com.github.caijh.framework.data.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.caijh.framework.data.redis.serializer.RedisNumberSerializer;
import com.github.caijh.framework.data.redis.serializer.RedisProtobufSerializer;
import com.github.caijh.framework.data.redis.support.Redis;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedisTest {

    private Redis redis;

    @BeforeEach
    public void setUp() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        String host = "127.0.0.1";
        int port = 6379;
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setPassword("redis");
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        connectionFactory.afterPropertiesSet();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new RedisProtobufSerializer());
        redisTemplate.setHashValueSerializer(new RedisProtobufSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        this.redis = new Redis(redisTemplate, null);
        this.redis.batchDelete("*");
    }

    @AfterEach
    public void tearDown() {
        this.redis = null;
    }

    @Test
    void test() {
        this.redis.set("integer", 1, Redis.Expired.NOT_EXPIRED);
        assertTrue(this.redis.hasKey("integer"));

        Integer integer = this.redis.get("integer");
        assertEquals(1, (int) integer);

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        this.redis.set("list", list);
        List<String> list1 = this.redis.get("list");
        assertEquals("a", list1.get(0));

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        this.redis.set("map", map);
        Map<String, Integer> map1 = this.redis.get("map");
        assertEquals(map.size(), map1.size());
        assertEquals(1, map1.get("a"));
        assertEquals(2, map1.get("b"));

        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        this.redis.setList("list2", list2);
        List<Integer> list21 = this.redis.getList("list2");
        assertEquals(list21.size(), list2.size());
        assertEquals(list21.get(0), list2.get(0));

        Boolean delResult = this.redis.delete("integer");
        assertTrue(delResult);

        Long num = this.redis.delete(Arrays.asList("list", "map", "list2"));
        assertEquals(3, num);

        this.redis.set("test", "test", 0L);
        this.redis.delete("test");

        redis.getRedisTemplate().opsForValue().increment("temp");
        Long temp = redis.get("temp", new RedisNumberSerializer<>(Long.class));
        assertEquals(1L, temp);

        this.redis.set("u:1", 1);
        this.redis.set("u:2", 2);
        this.redis.batchDelete("*");
        Set<String> keys = this.redis.getRedisTemplate().keys("*");
        assertTrue(keys == null || keys.isEmpty());
    }

    @Test
    void bitCount() {
        String key = "bit-test";
        this.redis.getRedisTemplate().opsForValue().setBit(key, 0, false);
        this.redis.getRedisTemplate().opsForValue().setBit(key, 1, true);
        this.redis.getRedisTemplate().opsForValue().setBit(key, 2, false);

        int count = this.redis.bitCount(key);
        assertEquals(1, count);
    }

}
