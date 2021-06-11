package com.github.caijh.framework.data.redis.support;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserSign<T> {

    private T userId;

    private LocalDate date;

}
