package com.github.caijh.framework.data.redis.support;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserSign<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -5098376714238285505L;
    private T userId;

    private LocalDate date;

}
