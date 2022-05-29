package com.github.caijh.framework.global.id.service;

import java.util.Random;

public interface GlobalIdService {

    default long generate(Long increase) {
        long saltA = new Random(System.currentTimeMillis()).nextLong(0, 256);
        long saltB = new Random(System.currentTimeMillis()).nextLong(0, 256);
        return increase << 16 | saltA << 8 | saltB;
    }

    long nextId();

}
