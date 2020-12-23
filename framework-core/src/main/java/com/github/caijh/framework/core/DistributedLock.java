package com.github.caijh.framework.core;

import java.util.concurrent.locks.Lock;

public interface DistributedLock {

    Lock get();

    Lock get(String key);

}
