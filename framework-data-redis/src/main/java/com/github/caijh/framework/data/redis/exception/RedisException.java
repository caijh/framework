package com.github.caijh.framework.data.redis.exception;

public class RedisException extends RuntimeException {

    public RedisException() {}

    public RedisException(Throwable e) {
        super(e);
    }

}
