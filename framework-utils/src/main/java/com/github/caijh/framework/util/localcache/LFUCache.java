package com.github.caijh.framework.util.localcache;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.github.caijh.commons.util.Asserts;

/**
 * 本地缓存工具类
 * <p>
 * 最不经常使用淘汰算法（Least Frequently Used）
 * </p>
 */
public class LFUCache<K, V> {

    final int size;
    private final ConcurrentHashMap<Object, CacheElement> concurrentHashMap;

    /**
     * 初始LFUCache.
     *
     * @param capacity    缓存长度
     * @param refreshTime 刷新时间，单位是分钟
     */
    public LFUCache(int capacity, int refreshTime) {
        this.size = capacity;
        this.concurrentHashMap = new ConcurrentHashMap<>(capacity);
        // 定时（每1分钟）清理一次缓存中过期的数据
        // 定时任务执行器
        ScheduledExecutorService refreshExecutor = new ScheduledThreadPoolExecutor(1, Executors.defaultThreadFactory());
        refreshExecutor.scheduleAtFixedRate(
            this::expireCache,
            0, refreshTime * 60L, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存
     *
     * @param key cache key
     * @return cache object value
     */
    public Object get(K key) {
        Asserts.notNull(key);
        if (this.concurrentHashMap.isEmpty()) {
            return null;
        }
        if (!this.concurrentHashMap.containsKey(key)) {
            return null;
        }
        CacheElement cache = this.concurrentHashMap.get(key);
        if (cache == null) {
            return null;
        }
        long timoutTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - cache.getWriteTime());
        if (cache.getExpireTime() <= timoutTime) { // 过期删除
            //清除过期缓存
            this.concurrentHashMap.remove(key);
            return null;
        }
        cache.setHitCount(cache.getHitCount() + 1);
        // 刷新最后访问时间
        cache.setAccessTime(new Date().getTime());
        return cache.getValue();
    }

    /**
     * 清除缓存
     *
     * @param key cache key to remove
     */
    public void remove(K key) {
        Asserts.notNull(key);
        if (this.concurrentHashMap.isEmpty()) {
            return;
        }
        if (!this.concurrentHashMap.containsKey(key)) {
            return;
        }
        this.concurrentHashMap.remove(key);
    }

    /**
     * 添加缓存
     *
     * @param key    cache key to add
     * @param value  cache value to add
     * @param expire expire seconds
     */
    public void put(K key, V value, long expire) {
        Asserts.notNull(key);
        Asserts.notNull(value);
        // 当缓存存在时，更新缓存
        if (this.concurrentHashMap.containsKey(key)) {
            CacheElement cache = this.concurrentHashMap.get(key);
            cache.setHitCount(cache.getHitCount() + 1);
            cache.setWriteTime(new Date().getTime());
            cache.setAccessTime(new Date().getTime());
            cache.setExpireTime(expire);
            cache.setValue(value);
            return;
        }
        // 已经达到最大缓存
        if (this.isFull()) {
            Object kickedKey = this.getKickedKey();
            if (kickedKey != null) {
                // 移除最少使用的缓存
                this.concurrentHashMap.remove(kickedKey);
            } else {
                return;
            }
        }
        CacheElement cache = new CacheElement();
        cache.setKey(key);
        cache.setValue(value);
        cache.setWriteTime(new Date().getTime());
        cache.setAccessTime(new Date().getTime());
        cache.setHitCount(1);
        cache.setExpireTime(expire);
        this.concurrentHashMap.put(key, cache);
    }

    /**
     * 判断是否达到最大缓存
     *
     * @return true if full.
     */
    private boolean isFull() {
        return this.concurrentHashMap.size() == this.size;
    }

    /**
     * 获取最少使用的缓存
     *
     * @return 最少用的cache缓存的值.
     */
    private Object getKickedKey() {
        CacheElement min = Collections.min(this.concurrentHashMap.values());
        return min.getKey();
    }

    /**
     * 创建多久后，缓存失效
     */
    private void expireCache() {
        //清除过期缓存
        this.concurrentHashMap.keySet().forEach(key -> {
            CacheElement cache = this.concurrentHashMap.get(key);
            long timoutTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()
                - cache.getWriteTime());
            if (cache.getExpireTime() > timoutTime) {
                return;
            }
            this.concurrentHashMap.remove(key);
        });
    }

}
