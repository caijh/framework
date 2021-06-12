package com.github.caijh.framework.data.redis.support;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;

import com.github.caijh.framework.data.redis.Redis;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.stereotype.Service;

@Service
public class UserSignSupportRedisImpl implements UserSignSupport {

    @Inject
    private Redis redis;

    @Override
    public <T extends Serializable> void sign(UserSign<T> userSign) {
        String key = this.buildKey(userSign.getUserId(), userSign.getDate());
        this.redis.getRedisTemplate().opsForValue().setBit(key, userSign.getDate().getDayOfMonth() - 1, true);
    }

    @Override
    public <T extends Serializable> boolean checkSign(UserSign<T> userSign) {
        String key = this.buildKey(userSign.getUserId(), userSign.getDate());
        Boolean bitResult = this.redis.getRedisTemplate().opsForValue().getBit(key, userSign.getDate().getDayOfMonth() - 1);
        return Optional.ofNullable(bitResult).orElse(false);
    }

    @Override
    public <T extends Serializable> int getSignCount(UserSign<T> userSign) {
        String key = this.buildKey(userSign.getUserId(), userSign.getDate());
        return this.redis.bitCount(key);
    }

    @Override
    public <T extends Serializable> List<UserSign<T>> list(T userId, YearMonth yearMonth) {
        String key = this.buildKey(userId, yearMonth.atEndOfMonth());
        int dayOfMonth = yearMonth.atEndOfMonth().getDayOfMonth();
        List<Long> bitFields = this.redis.getRedisTemplate().opsForValue().bitField(key,
                BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
        List<UserSign<T>> userSigns = new LinkedList<>();
        if (bitFields != null && !bitFields.isEmpty()) {
            // 由低位到高位，为0表示未签，为1表示已签
            long v = bitFields.get(0) == null ? 0 : bitFields.get(0);
            for (int i = dayOfMonth; i > 0; i--) {
                if (v >> 1 << 1 != v) {
                    UserSign<T> userSign = new UserSign<>();
                    userSign.setUserId(userId);
                    userSign.setDate(yearMonth.atDay(i));
                    userSigns.add(userSign);
                }
                v >>= 1;
            }
        }
        return userSigns;
    }

}
