package com.github.caijh.framework.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedisTest {

    private Redis redis;

    @BeforeAll
    public void setUp() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory("127.0.0.1", 6379);
        connectionFactory.afterPropertiesSet();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        redis = new Redis(redisTemplate);
    }

    @AfterAll
    public void tearDown() {
        redis = null;
    }

    @Test
    public void test() {
        redis.set("integer", 1);

        Integer integer = redis.get("integer", Integer.class);
        assertEquals(1, (int) integer);

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        redis.set("list", list);
        List list1 = redis.get("list", list.getClass());
        assertEquals("a", list1.get(0));

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        redis.set("map", map);
        Map map1 = redis.get("map", Map.class);
        assertEquals(map.size(), map1.size());
        assertEquals(1, map1.get("a"));
        assertEquals(2, map1.get("b"));

        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        redis.setList("list2", list2, Integer.class);
        List<Integer> list21 = redis.getList("list2", Integer.class);
        assertEquals(list21.size(), list2.size());
        assertEquals(list21.get(0), list2.get(0));

        redis.del("integer");
        redis.del(Arrays.asList("list", "map", "list2"));

        redis.set("test", "test", 0L);
        redis.del("test");

        redis.set("u:1", 1);
        redis.set("u:2", 2);
        redis.delBatch("*");
        Set<String> keys = redis.getRedisTemplate().keys("*");
        assertTrue(keys == null || keys.isEmpty());
    }

}