package com.github.caijh.framework.wechat.service

import com.github.caijh.framework.wechat.model.*
import java.io.File

interface WechatApis {
    /**
     * Get wechat api invoke access token.
     * @param wechatApp WechatApp
     * @return AccessToken
     */
    fun getAccessToken(wechatApp: WechatApp): AccessToken

    fun getJsApiTicket(wechatApp: WechatApp): String

    fun login(wechatApp: WechatApp, code: String): String

    fun getWechatUserInfo(wechatApp: WechatApp, openid: String): WechatUserInfo

    fun getWechatJsSdkConfig(wechatApp: WechatApp, url: String): WechatJsSdkConfig

    fun uploadMedia(wechatApp: WechatApp, type: String, file: File): WechatMedia
}
