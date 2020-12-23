package com.github.caijh.framework.core;

import java.util.concurrent.TimeUnit;

public interface DistributedLock {

    void acquire() throws Exception;

    boolean acquire(long time, TimeUnit timeUnit) throws Exception;

    boolean acquire(String key, long time, TimeUnit timeUnit) throws Exception;

    void release() throws Exception;

    void release(String key) throws Exception;

}
