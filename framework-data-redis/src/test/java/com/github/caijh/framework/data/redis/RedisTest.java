package com.github.caijh.framework.data.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.caijh.framework.data.redis.serializer.ProtobufSerializer;
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
        String host = "10.38.2.12";
        int port = 30379;
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        connectionFactory.afterPropertiesSet();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new ProtobufSerializer());
        redisTemplate.setHashValueSerializer(new ProtobufSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        redis = new Redis(redisTemplate);
    }

    @AfterEach
    public void tearDown() {
        redis = null;
    }

    @Test
    void test() {
        redis.set("integer", 1, Redis.Expired.NOT_EXPIRED);
        assertTrue(redis.hasKey("integer"));

        Integer integer = redis.get("integer");
        assertEquals(1, (int) integer);

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        redis.set("list", list);
        List<String> list1 = redis.get("list");
        assertEquals("a", list1.get(0));

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        redis.set("map", map);
        Map<String, Integer> map1 = redis.get("map");
        assertEquals(map.size(), map1.size());
        assertEquals(1, map1.get("a"));
        assertEquals(2, map1.get("b"));

        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        redis.setList("list2", list2);
        List<Integer> list21 = redis.getList("list2");
        assertEquals(list21.size(), list2.size());
        assertEquals(list21.get(0), list2.get(0));

        Boolean delResult = redis.delete("integer");
        assertTrue(delResult);

        Long num = redis.delete(Arrays.asList("list", "map", "list2"));
        assertEquals(3, num);

        redis.set("test", "test", 0L);
        redis.delete("test");

        redis.set("u:1", 1);
        redis.set("u:2", 2);
        redis.batchDelete("*");
        Set<String> keys = redis.getRedisTemplate().keys("*");
        assertTrue(keys == null || keys.isEmpty());
    }

}
