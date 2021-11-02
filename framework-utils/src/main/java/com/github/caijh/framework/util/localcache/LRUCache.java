package com.github.caijh.framework.util.localcache;

import java.util.concurrent.ConcurrentHashMap;

import com.github.caijh.commons.util.Asserts;

/**
 * 本地缓存工具类
 * <p>
 * 最近最少使用淘汰算法（Least Recently Used）
 * </p>
 */
public class LRUCache<K, V> {

    private final int capacity;
    private final ConcurrentHashMap<Object, Node> concurrentHashMap;
    private final Node head = new Node(-1, -1);
    private final Node tail = new Node(-1, -1);

    public LRUCache(int capacity) {
        // write your code here
        this.capacity = capacity;
        this.concurrentHashMap = new ConcurrentHashMap<>(capacity);
        this.tail.prev = this.head;
        this.head.next = this.tail;
    }

    /**
     * 获取缓存
     *
     * @param key key
     * @return the object in the cache.
     */
    public Object get(K key) {
        Asserts.notNull(key);
        if (this.concurrentHashMap.isEmpty()) {
            return null;
        }
        if (!this.concurrentHashMap.containsKey(key)) {
            return null;
        }
        Node current = this.concurrentHashMap.get(key);
        // 将当前链表移出
        current.prev.next = current.next;
        current.next.prev = current.prev;

        this.moveToTail(current);

        return current.value;
    }

    /**
     * 添加缓存
     *
     * @param key   key to cache
     * @param value value to cache
     */
    public void put(K key, V value) {
        Asserts.notNull(key);
        Asserts.notNull(value);
        // 当缓存存在时，更新缓存
        if (this.concurrentHashMap.containsKey(key)) {
            Node current = this.concurrentHashMap.get(key);
            // 将当前链表移出
            current.prev.next = current.next;
            current.next.prev = current.prev;

            this.moveToTail(current);
            return;
        }
        // 已经达到最大缓存
        if (this.isFull()) {
            this.concurrentHashMap.remove(this.head.next.key);
            this.head.next.next.prev = this.head;
            this.head.next = this.head.next.next;
        }
        Node node = new Node(key, value);
        this.concurrentHashMap.put(key, node);
        this.moveToTail(node);
    }

    /**
     * 判断是否达到最大缓存
     *
     * @return true if size equals capacity.
     */
    private boolean isFull() {
        return this.concurrentHashMap.size() == this.capacity;
    }

    private void moveToTail(Node current) {
        // 将当前链表添加到尾部
        this.tail.prev.next = current;
        current.prev = this.tail.prev;
        this.tail.prev = current;
        current.next = this.tail;
    }

    private static class Node {

        Node prev;
        Node next;
        Object key;
        Object value;

        public Node(Object key, Object value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }

    }

}
