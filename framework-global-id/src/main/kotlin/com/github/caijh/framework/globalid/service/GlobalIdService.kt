package com.github.caijh.framework.globalid.service

import kotlin.random.Random

interface GlobalIdService {

    fun generate(increase: Long): Long {
        val saltA = Random(System.currentTimeMillis()).nextLong(0L, 256L)
        val saltB = Random(System.currentTimeMillis()).nextLong(0L, 256L)
        return increase.shl(16) or (saltA.shl(8)) or saltB
    }

    fun nextId(): Long
}
