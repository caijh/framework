package com.github.caijh.framework.global.id.service;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

import com.github.caijh.framework.core.lock.LockManager;
import com.github.caijh.framework.data.redis.support.Redis;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.redisson.api.RBoundedBlockingQueue;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/*
 * 薄雾算法
 *
 * 1      2                                                     48         56       64
 * +------+-----------------------------------------------------+----------+----------+
 * retain | increas                                             | salt     | sequence |
 * +------+-----------------------------------------------------+----------+----------+
 * 0      | 0000000000 0000000000 0000000000 0000000000 0000000 | 00000000 | 00000000 |
 * +------+-----------------------------------------------------+------------+--------+
 *
 * 0. 最高位，占 1 位，保持为 0，使得值永远为正数；
 * 1. 高位数，占 47 位，高位数（必须是自增数）在高位能保证结果值呈递增态势，遂低位可以为所欲为；
 * 2. 随机因子一，占 8 位，上限数值 255，使结果值不可预测；
 * 3. 随机因子二，占 8 位，上限数值 255，使结果值不可预测；
 *
 * 编号上限为百万亿级，上限值计算为 140737488355327 即 int64(1 << 47 - 1)，假设每天取值 10 亿，能使用 385+ 年
 */
@Service
public class MistGlobalIdServiceImpl implements GlobalIdService, InitializingBean, Runnable {

    private static final int MAX_SIZE = 100;

    @Inject
    private Redis redis;
    @Inject
    private LockManager lockManager;

    private RBoundedBlockingQueue<Long> blockingQueue;

    @SneakyThrows
    @Override
    public long nextId() {
        return blockingQueue.take();
    }

    @Override
    public void afterPropertiesSet() {
        blockingQueue = redis.getRedissonClient().getBoundedBlockingQueue("global:id:seq");
        blockingQueue.trySetCapacity(MAX_SIZE);
        Executors.newFixedThreadPool(1).execute(this);
    }

    @SneakyThrows
    @Override
    public void run() {
        Lock lock = lockManager.get("global:id:lock");
        try { // 确认顺序放入blockingQueue中
            lock.lockInterruptibly();
            if (blockingQueue.size() < MAX_SIZE) {
                Long increase = redis.getRedisTemplate().opsForValue().increment("global:id", 1L);
                if (increase != null) {
                    blockingQueue.put(generate(increase));
                }
            }
        } finally {
            lock.unlock();
        }
    }

}
