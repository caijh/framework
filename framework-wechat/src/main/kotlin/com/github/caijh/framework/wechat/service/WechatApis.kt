package com.github.caijh.framework.wechat.service

import com.github.caijh.framework.wechat.model.AccessToken
import com.github.caijh.framework.wechat.model.WechatApp

interface WechatApis {
    /**
     * Get wechat api invoke access token.
     * @param wechatApp WechatApp
     * @return AccessToken
     */
    fun getAccessToken(wechatApp: WechatApp): AccessToken

    fun getJsApiTicket(wechatApp: WechatApp): String

    fun login(wechatApp: WechatApp, code: String): String
}
