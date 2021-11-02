package com.github.caijh.framework.util.localcache;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

/**
 * 缓存元素
 */
public class CacheElement implements Comparable<CacheElement> {

    /**
     * 键
     */
    private Object key;
    /**
     * 缓存值
     */
    private Object value;
    /**
     * 最后一次访问时间
     */
    private long accessTime;
    /**
     * 创建时间
     */
    private long writeTime;
    /**
     * 存活时间
     */
    private long expireTime;
    /**
     * 命中次数
     */
    private Integer hitCount;

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getAccessTime() {
        return this.accessTime;
    }

    public void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }

    public long getWriteTime() {
        return this.writeTime;
    }

    public void setWriteTime(long writeTime) {
        this.writeTime = writeTime;
    }

    public Integer getHitCount() {
        return this.hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    public Object getKey() {
        return this.key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public long getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CacheElement that = (CacheElement) o;
        return this.key.equals(that.key) && this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.value);
    }

    @Override
    public int compareTo(@NotNull CacheElement o) {
        return this.hitCount.compareTo(o.hitCount);
    }

}
