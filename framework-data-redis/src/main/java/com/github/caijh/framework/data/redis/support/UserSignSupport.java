package com.github.caijh.framework.data.redis.support;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.github.caijh.framework.core.constants.DateFormat;

public interface UserSignSupport {

    /**
     * 用户签到.
     *
     * @param userSign UserSign
     * @param <T>      the type uid
     */
    <T> void sign(UserSign<T> userSign);

    /**
     * 用户是否签到.
     *
     * @param userSign UserSign
     * @param <T>      the type uid
     * @return true, if user sign.
     */
    <T> boolean checkSign(UserSign<T> userSign);

    /**
     * 获取用户签到的次数.
     *
     * @param userSign UserSign
     * @param <T>      the type uid
     * @return 获取用户签到的次数.
     */
    <T> int getSignCount(UserSign<T> userSign);

    /**
     * 获取用户某一月的签到记录.
     *
     * @param userId    用户id
     * @param yearMonth YearMonth
     * @param <T>       the type uid
     * @return 用户某一月的签到记录.
     */
    <T> List<UserSign<T>> list(T userId, YearMonth yearMonth);

    default <T> String buildKey(T uid, LocalDate localDate) {
        return "u:sign:" + uid + ":" + localDate.format(DateTimeFormatter.ofPattern(DateFormat.YYYYMM));
    }

}
