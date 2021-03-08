package com.github.caijh.framework.wechat.service

import com.github.caijh.framework.wechat.model.AccessToken
import com.github.caijh.framework.wechat.model.WechatApp

interface WechatApis {
    fun getAccessToken(wechatApp: WechatApp): AccessToken

    fun getJsApiTicket(wechatApp: WechatApp): String
}
