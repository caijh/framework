package com.github.caijh.framework.global.id.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import com.github.caijh.framework.core.lock.aspect.LockManager;
import com.github.caijh.framework.data.redis.support.Redis;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/*
 * 薄雾算法
 *
 * 1      2                                                     48         56       64
 * +------+-----------------------------------------------------+----------+----------+
 * retain | increase                                             | salt     | sequence |
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
public class MistGlobalIdServiceImpl implements GlobalIdService {

    private Redis redis;
    private LockManager lockManager;

    @Inject
    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    @Inject
    public void setLockManager(LockManager lockManager) {
        this.lockManager = lockManager;
    }

    @Override
    public long nextId() {
        return generate_id("default");
    }

    @Override
    public long nextId(String table) {
        return generate_id(table);
    }

    @Override
    public List<Long> nextIds(int n) {
        return nextIds("default", n);
    }


    @SneakyThrows
    @Override
    public List<Long> nextIds(String table, int n) {
        Lock lock = lockManager.get("global:id:lock:" + table);
        try {
            lock.lockInterruptibly();
            List<Long> ids = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                ids.add(generate_id(table));
            }
            return ids;
        } finally {
            lock.unlock();
        }
    }

    private long generate_id(String table) {
        Long increase = redis.getRedisTemplate().opsForValue().increment("global:id:" + table, 1L);
        return generate(increase);
    }

}
