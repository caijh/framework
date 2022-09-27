package com.github.caijh.framework.core.lock;

import java.util.concurrent.locks.Lock;

public interface DistributedLock {

    Lock get();

    Lock get(String key);

}
