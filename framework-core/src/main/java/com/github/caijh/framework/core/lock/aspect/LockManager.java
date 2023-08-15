package com.github.caijh.framework.core.lock.aspect;

import java.util.concurrent.locks.Lock;

public interface LockManager {

    Lock get();

    Lock get(String key);

}
