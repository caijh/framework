package com.github.caijh.framework.wechat.service.impl

import com.github.caijh.framework.wechat.model.WechatApp
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class WechatApisImplTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getAccessToken() {
        val wechatApp = WechatApp("wx3122a2651783fb93", "36e1aec13f873b5965a9f85e4e56c6c4")
        val wechatApisImpl = WechatApisImpl()
        val accessToken = wechatApisImpl.getAccessToken(wechatApp)
        Assertions.assertNotNull(accessToken.token)
    }

}
