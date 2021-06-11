package com.github.caijh.framework.data.redis.support;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface UserSignService {

    <T> void sign(UserSign<T> userSign);

    <T> boolean checkSign(UserSign<T> userSign);

    <T> int getSignCount(UserSign<T> userSign);

    <T> List<UserSign<T>> list(T userId, YearMonth yearMonth);

    default <T> String buildKey(T uid, LocalDate localDate) {
        return "u:sign:" + uid + ":" + localDate.format(DateTimeFormatter.ofPattern("yyyyMM"));
    }

}
