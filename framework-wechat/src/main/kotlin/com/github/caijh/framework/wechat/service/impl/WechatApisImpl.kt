package com.github.caijh.framework.wechat.service.impl

import com.alibaba.fastjson.JSON
import com.github.caijh.commons.util.HttpClientUtils
import com.github.caijh.framework.wechat.constants.WechatConstants
import com.github.caijh.framework.wechat.exception.WechatApiException
import com.github.caijh.framework.wechat.model.AccessToken
import com.github.caijh.framework.wechat.model.WechatApp
import com.github.caijh.framework.wechat.model.WechatRespBody
import com.github.caijh.framework.wechat.service.WechatApis
import org.springframework.stereotype.Service

@Service
class WechatApisImpl : WechatApis {
    override fun getAccessToken(wechatApp: WechatApp): AccessToken {
        val url =
            WechatConstants.API_URL + WechatConstants.API_TOKEN_URI + "&appid=${wechatApp.appId}&secret=${wechatApp.secret}"
        val respBody = HttpClientUtils.get(url)
        println(respBody)
        val data = JSON.parseObject(respBody, WechatRespBody::class.java)
        if (!data.success()) {
            throw WechatApiException(data.errcode(), data.errmsg())
        }
        return JSON.parseObject(respBody, AccessToken::class.java)
    }
}
