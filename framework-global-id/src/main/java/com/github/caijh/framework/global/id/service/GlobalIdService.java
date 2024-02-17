package com.github.caijh.framework.global.id.service;

import java.security.SecureRandom;

public interface GlobalIdService {

    default long generate(Long increase) {
        long saltA = new SecureRandom().nextLong(0L, 256L);
        long saltB = new SecureRandom().nextLong(0L, 256L);
        return increase << 16 | saltA << 8 | saltB;
    }

    long nextId();

}
