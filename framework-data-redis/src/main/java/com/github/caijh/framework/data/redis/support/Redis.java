package com.github.caijh.framework.data.redis.support;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import com.github.caijh.framework.data.redis.exception.RedisScanException;
import com.github.caijh.framework.data.redis.exception.RedisSetValueException;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import static com.github.caijh.framework.data.redis.support.Redis.Expired.ENTITY_EXPIRED_SECONDS;
import static com.github.caijh.framework.data.redis.support.Redis.Expired.LIST_EXPIRED_SECONDS;

/**
 * Redis通用方法集对象.
 */
public class Redis {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisSerializer<String> keySerializer;
    private final RedisSerializer<Object> valueSerializer;
    private final RedissonClient redissonClient;
    private final RedisLock redisLock;

    @SuppressWarnings("unchecked")
    public Redis(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
        this.redisLock = new RedisLock(this.redissonClient);
        this.keySerializer = (RedisSerializer<String>) this.redisTemplate.getKeySerializer();
        this.valueSerializer = (RedisSerializer<Object>) this.redisTemplate.getValueSerializer();
    }

    public byte[] getRawValue(String key) {
        byte[] keyBytes = this.keySerializer.serialize(key);
        Assert.notNull(keyBytes, "key must not be null");
        return this.redisTemplate.execute((RedisConnection redisConnection) -> redisConnection.get(keyBytes));
    }

    public <T> T get(String key, RedisSerializer<T> valueSerializer) {
        byte[] rawValue = getRawValue(key);

        return valueSerializer.deserialize(rawValue);
    }

    public <T> T get(String key, String hashKey, RedisSerializer<T> valueSerializer) {
        byte[] rawKey = this.keySerializer.serialize(key);
        @SuppressWarnings("unchecked")
        RedisSerializer<String> hashKeySerializer = (RedisSerializer<String>) getRedisTemplate().getHashKeySerializer();
        byte[] rawHashKey = hashKeySerializer.serialize(hashKey);
        Assert.notNull(rawKey, "key must not null");
        Assert.notNull(rawHashKey, "hash key must not null");

        byte[] bytes = redisTemplate.execute((RedisConnection redisConnection) -> redisConnection.hashCommands().hGet(rawKey, rawHashKey));
        return valueSerializer.deserialize(bytes);
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
        byte[] valueBytes = getRawValue(key);

        return (T) this.valueSerializer.deserialize(valueBytes);
    }

    /**
     * save obj to redis.
     *
     * @param key redis key
     * @param obj the obj to save
     * @param <T> obj class
     */
    public <T> void set(String key, T obj) {
        this.set(key, obj, null);
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
        final byte[] keyBytes = this.keySerializer.serialize(key);
        byte[] serialize = this.valueSerializer.serialize(obj);
        if (expire == null) {
            Class<T> clazz = (Class<T>) obj.getClass();
            if (clazz.isArray() || Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz)) {
                expire = LIST_EXPIRED_SECONDS;
            } else {
                expire = ENTITY_EXPIRED_SECONDS;
            }
        }

        this.setEx(keyBytes, serialize, expire);
    }

    private void setEx(byte[] keyBytes, byte[] serializeValue, Long expire) {
        boolean ret;
        if (expire > 0) {
            ret = Optional
                .ofNullable(this.getRedisTemplate().execute((RedisConnection redisConnection) -> redisConnection.setEx(keyBytes, expire, serializeValue)))
                .orElse(true);
        } else {
            ret = Optional.ofNullable(this.getRedisTemplate().execute((RedisConnection redisConnection) -> redisConnection.set(keyBytes, serializeValue)))
                          .orElse(true);
        }
        if (!ret) {
            throw new RedisSetValueException();
        }
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    /**
     * save list to redis.
     *
     * @param key  redis key
     * @param list list
     * @param <T>  type of class
     */
    public <T> void setList(String key, List<T> list) {
        this.setList(key, list, LIST_EXPIRED_SECONDS);
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
        final byte[] keyBytes = this.keySerializer.serialize(key);
        byte[] serialize = this.valueSerializer.serialize(list);
        if (expire == null) {
            expire = LIST_EXPIRED_SECONDS;
        }
        this.setEx(keyBytes, serialize, expire);
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
        byte[] valueBytes = getRawValue(key);
        return (List<T>) this.valueSerializer.deserialize(valueBytes);
    }

    /**
     * delete keys match pattern.
     *
     * @param pattern pattern
     */
    public void batchDelete(String pattern) {
        Set<String> keys = new HashSet<>();
        this.scan(pattern, bytes -> {
            String key = this.keySerializer.deserialize(bytes);
            keys.add(key);
        });
        this.delete(keys);
    }

    public void scan(final String pattern, Consumer<byte[]> consumer) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            } catch (Exception e) {
                throw new RedisScanException(e);
            }
        });
    }

    public void scan(final String pattern, int offset, int limit, Consumer<byte[]> consumer, TotalCounter counter) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(pattern).build())) {
                int start = -1;
                int end = offset + limit;
                boolean calcTotal = counter != null && counter.calcTotal();
                while (cursor.hasNext()) {
                    start++;
                    byte[] next = cursor.next();
                    if (start >= offset && start < end) {
                        consumer.accept(next);
                    }
                    if (!calcTotal && start >= end) {
                        break;
                    }
                }
                if (calcTotal) {
                    counter.setTotal(start + 1L);
                }
                return null;
            } catch (Exception e) {
                throw new RedisScanException(e);
            }
        });
    }

    public Set<String> keys(String pattern) {
        Set<String> keys = new LinkedHashSet<>();
        this.scan(pattern, bytes -> {
            String key = this.keySerializer.deserialize(bytes);
            keys.add(key);
        });
        return keys;
    }

    public Set<String> keys(String pattern, int offset, int limit, TotalCounter totalCounter) {
        Set<String> keys = new LinkedHashSet<>();
        this.scan(pattern, offset, limit, bytes -> {
            String key = this.keySerializer.deserialize(bytes);
            keys.add(key);
        }, totalCounter);
        return keys;
    }

    /**
     * delete keys.
     *
     * @param keys keys to delete
     * @return the number of keys is deleted
     */
    public Long delete(Collection<String> keys) {
        return this.redisTemplate.delete(keys);
    }

    /**
     * delete the key.
     *
     * @param key key
     * @return true, if delete successful.
     */
    public Boolean delete(String key) {
        return this.redisTemplate.delete(key);
    }

    /**
     * 判断key是否在redis中.
     *
     * @param key key
     * @return true, if redis has the key.
     */
    public boolean hasKey(String key) {
        Boolean hasKey = this.getRedisTemplate().hasKey(key);
        return Optional.ofNullable(hasKey).orElse(false);
    }

    public int bitCount(String key) {
        byte[] keyByte = this.keySerializer.serialize(key);
        Assert.notNull(keyByte, "key 不能为空");
        Long count = this.getRedisTemplate().execute((RedisCallback<Long>) con -> con.bitCount(keyByte));
        return count != null ? count.intValue() : 0;
    }

    public RedissonClient getRedissonClient() {
        return this.redissonClient;
    }

    public RedisLock getLock() {
        return this.redisLock;
    }

    public Date getExpiredAt(String key) {
        long expire = Optional.ofNullable(getRedisTemplate().getExpire(key)).orElse(-1L);
        Date expiredAt = null;
        if (expire >= 0) {
            expiredAt = Date.from(LocalDateTime.now().plusSeconds(expire).atZone(ZoneId.systemDefault()).toInstant());
        }
        return expiredAt;
    }

    public static class Expired {

        public static final long NOT_EXPIRED = -1;
        /**
         * 列表默认缓存30秒
         */
        public static final long LIST_EXPIRED_SECONDS = 30;

        /**
         * 1分钟，单位秒.
         */
        public static final long M_1_SECONDS = 60L;

        /**
         * 实体对象默认缓存一天
         */
        public static final long ENTITY_EXPIRED_SECONDS = Expired.M_1_SECONDS * 60 * 24;

        private Expired() {}

    }

}
