package com.github.caijh.framework.data.redis.support.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;

import com.beust.jcommander.internal.Sets;
import com.github.caijh.framework.data.redis.support.FansSupport;
import com.github.caijh.framework.data.redis.support.Redis;
import org.springframework.stereotype.Service;

@Service
public class FansSupportImpl implements FansSupport {

    private static final String FOLLOW_KEY = "u:%s:follow";
    private static final String FANS_KEY = "u:%s:fans";

    @Inject
    private Redis redis;

    @Override
    public <T extends Number> void follow(T uid, T targetUid) {
        this.redis.getRedisTemplate().opsForSet().add(String.format(FansSupportImpl.FOLLOW_KEY, uid), targetUid);
        this.redis.getRedisTemplate().opsForSet().add(String.format(FansSupportImpl.FANS_KEY, targetUid), uid);
    }

    @Override
    public <T extends Number> void unfollow(T uid, T targetUid) {
        this.redis.getRedisTemplate().opsForSet().remove(String.format(FansSupportImpl.FOLLOW_KEY, uid), targetUid);
        this.redis.getRedisTemplate().opsForSet().remove(String.format(FansSupportImpl.FANS_KEY, targetUid), uid);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Number> Set<T> getFollowUid(T uid) {
        Set<Object> members = this.redis.getRedisTemplate().opsForSet().members(String.format(FansSupportImpl.FOLLOW_KEY, uid));
        return Optional.ofNullable(members).orElse(Sets.newHashSet())
                       .stream().map(e -> (T) e)
                       .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Number> Set<T> getFansUid(T uid) {
        Set<Object> members = this.redis.getRedisTemplate().opsForSet().members(String.format(FansSupportImpl.FANS_KEY, uid));
        return Optional.ofNullable(members).orElse(Sets.newHashSet())
                       .stream().map(e -> (T) e)
                       .collect(Collectors.toSet());
    }

    @Override
    public <T extends Number> boolean isFollowed(T uid, T targetUid) {
        Boolean isFollowed = this.redis.getRedisTemplate().opsForSet().isMember(String.format(FansSupportImpl.FOLLOW_KEY, uid), targetUid);
        return Optional.ofNullable(isFollowed).orElse(false);
    }

    @Override
    public <T extends Number> long getMyFollowedNum(T uid) {
        return Optional.ofNullable(this.redis.getRedisTemplate().opsForSet().size(String.format(FansSupportImpl.FOLLOW_KEY, uid))).orElse(0L);
    }

    @Override
    public <T extends Number> boolean isMyFans(T uid, T targetUid) {
        Boolean isMyFans = this.redis.getRedisTemplate().opsForSet().isMember(String.format(FansSupportImpl.FANS_KEY, uid), targetUid);
        return Optional.ofNullable(isMyFans).orElse(false);
    }

    @Override
    public <T extends Number> long getMyFansNum(T uid) {
        return Optional.ofNullable(this.redis.getRedisTemplate().opsForSet().size(String.format(FansSupportImpl.FANS_KEY, uid))).orElse(0L);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Number> Set<T> getFollowIntersection(T uid, T targetUid) {
        Set<Object> intersect = this.redis.getRedisTemplate().opsForSet()
                                          .intersect(String.format(FansSupportImpl.FANS_KEY, uid), String.format(FansSupportImpl.FANS_KEY, targetUid));
        return Optional.ofNullable(intersect).orElse(Sets.newHashSet()).stream().map(e -> (T) e)
                       .collect(Collectors.toSet());
    }

}
