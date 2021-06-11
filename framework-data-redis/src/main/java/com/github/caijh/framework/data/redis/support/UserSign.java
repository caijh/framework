package com.github.caijh.framework.data.redis.support;

import java.time.LocalDate;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserSign<T> {

    private T userId;

    private LocalDate date;

}
