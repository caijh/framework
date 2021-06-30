package com.github.caijh.framework.data.redis.support;

import java.util.Set;

public interface FansSupport {

    <T extends Number> void follow(T uid, T targetUid);

    <T extends Number> void unfollow(T uid, T targetUid);

    <T extends Number> Set<T> getFollowUid(T uid);

    <T extends Number> Set<T> getFansUid(T uid);

    /**
     * 用户uid是否关注用户targetUid
     *
     * @param uid       用户id
     * @param targetUid 另一个用户id
     * @param <T>       targetUid
     * @return true or false
     */
    <T extends Number> boolean isFollowed(T uid, T targetUid);

    <T extends Number> long getMyFollowedNum(T uid);

    /**
     * 用户targetUid是否uid的粉丝.
     *
     * @param uid       用户id
     * @param targetUid 另一个用户id
     * @param <T>       type of uid
     * @return true or false
     */
    <T extends Number> boolean isMyFans(T uid, T targetUid);

    /**
     * 获取用户的粉丝数.
     *
     * @param uid 用户id
     * @param <T> type of uid
     * @return 用户粉丝数
     */
    <T extends Number> long getMyFansNum(T uid);

    /**
     * 获取两个用户的关注交集.
     *
     * @param uid       用户id
     * @param targetUid 另一个用户id
     * @param <T>       type of uid
     * @return 两个用户的关注交集
     */
    <T extends Number> Set<T> getFollowIntersection(T uid, T targetUid);

}
