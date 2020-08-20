package com.github.caijh.framework.redis;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.github.caijh.framework.redis.exception.RedisException;
import com.github.caijh.framework.redis.util.ProtoStuffSerializerUtils;
import com.github.caijh.framework.redis.util.Wrapper;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Redis {

    private static final long LIST_EXPIRED_SECONDS = 30; // 30秒
    private static final long ENTITY_EXPIRED_SECONDS = 60 * 60 * 24L; // 1天

    private final RedisTemplate<String, Object> redisTemplate;

    public Redis(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
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
        Class<T> clazz = (Class<T>) obj.getClass();
        if (clazz.isArray() || Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz)) {
            Wrapper<T> wrapper = new Wrapper<>(obj);
            set(key, wrapper, Wrapper.class, expire != null ? expire : LIST_EXPIRED_SECONDS);
        } else {
            set(key, obj, clazz, expire != null ? expire : ENTITY_EXPIRED_SECONDS);
        }
    }

    private <T> void set(String key, T obj, Class<T> typeClass, Long expire) {
        final byte[] keyBytes = key.getBytes(UTF_8);
        byte[] serialize = ProtoStuffSerializerUtils.serialize(obj, typeClass);
        setEx(keyBytes, serialize, expire);
    }

    /**
     * save list to redis.
     *
     * @param key   redis key
     * @param list  list
     * @param clazz the class of element in list
     * @param <T>   type of class
     */
    public <T> void setList(String key, List<T> list, Class<T> clazz) {
        setList(key, list, clazz, LIST_EXPIRED_SECONDS);
    }

    /**
     * set list to redis.
     *
     * @param key    redis key
     * @param list   list
     * @param clazz  the class of element in list
     * @param expire expire seconds
     * @param <T>    type name of element in list
     */
    public <T> void setList(String key, List<T> list, Class<T> clazz, Long expire) {
        final byte[] keyBytes = key.getBytes(UTF_8);
        byte[] serialize = ProtoStuffSerializerUtils.serialize(list, clazz);
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
     * get cache object.
     *
     * @param key   key
     * @param clazz value的对象的class
     * @param <T>   parameter type of class
     * @return T the has been cache object.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        byte[] result = getRedisTemplate().execute((RedisConnection redisConnection) -> redisConnection.get(key.getBytes(UTF_8)));
        if (result == null) {
            return null;
        }

        if (clazz.isArray() || Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz)) {
            return (T) ProtoStuffSerializerUtils.deserialize(result, Wrapper.class).getData();
        }

        return ProtoStuffSerializerUtils.deserialize(result, clazz);
    }

    /**
     * get the cached list.
     *
     * @param key   the key of object
     * @param clazz the list element type
     * @param <T>   parameter type name of clazz
     * @return list contains object of class T
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        byte[] result = redisTemplate.execute((RedisConnection redisConnection) -> redisConnection.get(key.getBytes(UTF_8)));

        if (result == null) {
            return Collections.emptyList();
        }

        return ProtoStuffSerializerUtils.deserializeList(result, clazz);
    }

    /**
     * delete the key.
     *
     * @param key key
     * @return true, if delete successful.
     */
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * delete keys.
     *
     * @param keys keys to delete
     * @return how much keys is deleted
     */
    public Long del(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    public void delBatch(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        del(keys);
    }

}
