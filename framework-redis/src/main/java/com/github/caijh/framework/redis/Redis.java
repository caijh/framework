package com.github.caijh.framework.redis;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import com.github.caijh.framework.redis.exception.RedisException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Redis {

    private static final long LIST_EXPIRED_SECONDS = 30; // 30秒
    private static final long ENTITY_EXPIRED_SECONDS = 60 * 60 * 24L; // 1天

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisSerializer<String> keySerializer;
    private final RedisSerializer<Object> valueSerializer;

    @SuppressWarnings("unchecked")
    public Redis(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        keySerializer = (RedisSerializer<String>) this.redisTemplate.getKeySerializer();
        valueSerializer = (RedisSerializer<Object>) this.redisTemplate.getValueSerializer();
    }

    /**
     * save obj to redis.
     *
     * @param key redis key
     * @param obj the obj to save
     * @param <T> obj class
     */
    public <T> void set(String key, T obj) {
        set(key, obj, null);
    }

    /**
     * save obj to redis.
     *
     * @param key    redis key
     * @param obj    the object to save
     * @param expire expire time, second
     * @param <T>    the class of object
     */
    @SuppressWarnings("unchecked")
    public <T> void set(String key, T obj, Long expire) {
        final byte[] keyBytes = keySerializer.serialize(key);
        byte[] serialize = valueSerializer.serialize(obj);
        if (expire == null) {
            Class<T> clazz = ((Class<T>) obj.getClass());
            if (clazz.isArray() || Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz)) {
                expire = LIST_EXPIRED_SECONDS;
            } else {
                expire = ENTITY_EXPIRED_SECONDS;
            }
        }

        setEx(keyBytes, serialize, expire);
    }


    /**
     * get cache object.
     *
     * @param key key
     * @param <T> parameter type of class
     * @return T the has been cache object.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        byte[] keyBytes = keySerializer.serialize(key);
        Assert.notNull(keyBytes, "key must not be null");
        byte[] result = redisTemplate.execute((RedisConnection redisConnection) -> redisConnection.get(keyBytes));
        if (result == null) {
            return null;
        }

        return (T) valueSerializer.deserialize(result);
    }


    /**
     * save list to redis.
     *
     * @param key  redis key
     * @param list list
     * @param <T>  type of class
     */
    public <T> void setList(String key, List<T> list) {
        setList(key, list, LIST_EXPIRED_SECONDS);
    }

    /**
     * set list to redis.
     *
     * @param key    redis key
     * @param list   list
     * @param expire expire seconds
     * @param <T>    type name of element in list
     */
    public <T> void setList(String key, List<T> list, Long expire) {
        final byte[] keyBytes = keySerializer.serialize(key);
        byte[] serialize = valueSerializer.serialize(list);
        setEx(keyBytes, serialize, expire);
    }

    private void setEx(byte[] keyBytes, byte[] serializeValue, Long expire) {
        boolean ret;
        if (expire > 0) {
            ret = Optional
                    .ofNullable(getRedisTemplate().execute((RedisConnection redisConnection) -> redisConnection.setEx(keyBytes, expire, serializeValue)))
                    .orElse(true);
        } else {
            ret = Optional.ofNullable(getRedisTemplate().execute((RedisConnection redisConnection) -> redisConnection.set(keyBytes, serializeValue)))
                          .orElse(true);
        }
        if (!ret) {
            throw new RedisException();
        }
    }

    /**
     * get the cached list.
     *
     * @param key the key of object
     * @param <T> parameter type name of clazz
     * @return list contains object of class T
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String key) {
        byte[] result = redisTemplate.execute((RedisConnection redisConnection) -> redisConnection.get(key.getBytes(UTF_8)));

        if (result == null) {
            return Collections.emptyList();
        }

        return (List<T>) valueSerializer.deserialize(result);
    }

    /**
     * delete the key.
     *
     * @param key key
     * @return true, if delete successful.
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * delete keys.
     *
     * @param keys keys to delete
     * @return how much keys is deleted
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * delete keys match pattern.
     *
     * @param pattern pattern
     */
    public void batchDelete(String pattern) {
        Set<String> keys = new HashSet<>();
        this.scan(pattern, bytes -> {
            String key = keySerializer.deserialize(bytes);
            keys.add(key);
        });
        delete(keys);
    }

    public void scan(final String pattern, Consumer<byte[]> consumer) {
        redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(2000).match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            } catch (Exception e) {
                throw new RedisException(e);
            }
        });
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

}
