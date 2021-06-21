package com.github.caijh.framework.globalid

import com.github.caijh.framework.globalid.service.MistGlobalIdServiceImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MistGlobalIdServiceTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun generate() {
        val globalIdService = MistGlobalIdServiceImpl()
        for (i in 1L..1000L) {
            val id = globalIdService.generate(i)
            println(id)
        }
    }

}
