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
        val respBody = doGet(url)
        return JSON.parseObject(respBody, AccessToken::class.java)
    }

    override fun getJsApiTicket(wechatApp: WechatApp): String {
        val accessToken = this.getAccessToken(wechatApp)
        val url =
            "${WechatConstants.API_URL}${WechatConstants.API_JS_API_TICKET}?&access_token=${accessToken.token()}&type=jsapi"
        val respBody = doGet(url)
        return JSON.parseObject(respBody).getString("ticket")
    }

    private fun doGet(url: String): String? {
        val respBody = HttpClientUtils.get(url)
        val data = JSON.parseObject(respBody, WechatRespBody::class.java)
        if (!data.success()) {
            throw WechatApiException(data.errcode(), data.errmsg())
        }
        return respBody
    }
}
