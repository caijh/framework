package com.github.caijh.framework.global.id.service;

import java.security.SecureRandom;
import java.util.List;

public interface GlobalIdService {

    default long generate(Long increase) {
        long saltA = new SecureRandom().nextLong(0L, 256L);
        long saltB = new SecureRandom().nextLong(0L, 256L);
        return increase << 16 | saltA << 8 | saltB;
    }

    long nextId();

    long nextId(String table);

    List<Long> nextIds(int n);

    List<Long> nextIds(String table, int n);

}
