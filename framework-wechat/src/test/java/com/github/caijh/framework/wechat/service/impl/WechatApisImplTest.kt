package com.github.caijh.framework.wechat.service.impl

import com.github.caijh.framework.wechat.enums.WechatAppType
import com.github.caijh.framework.wechat.model.WechatApp
import com.github.caijh.framework.wechat.service.WechatApis
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class WechatApisImplTest {
    private val wechatApp = WechatApp("", "", WechatAppType.MINI)
    private lateinit var wechatApis: WechatApis

    @BeforeEach
    fun setUp() {
        wechatApis = WechatApisImpl()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getAccessToken() {
        val accessToken = wechatApis.getAccessToken(wechatApp)
        Assertions.assertNotNull(accessToken.token)
    }

    @Test
    fun getJsTicket() {
        val jsApiTicket = wechatApis.getJsApiTicket(wechatApp)
        println(jsApiTicket)
    }

    @Test
    fun getWechatJsSdkConfig() {
        val wechatJsSdkConfig = wechatApis.getWechatJsSdkConfig(wechatApp, "www.baidu.com")
        println(wechatJsSdkConfig)
    }

    @Test
    fun getIndustryInfo() {
        val industryInfo = wechatApis.getIndustryInfo(wechatApp)
        Assertions.assertNotNull(industryInfo)
    }

}
