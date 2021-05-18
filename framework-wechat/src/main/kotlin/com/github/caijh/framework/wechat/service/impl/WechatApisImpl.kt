package com.github.caijh.framework.wechat.service.impl

import com.alibaba.fastjson.JSON
import com.github.caijh.commons.util.DateUtils
import com.github.caijh.commons.util.HttpClientUtils
import com.github.caijh.commons.util.Strings
import com.github.caijh.commons.util.UuidUtils
import com.github.caijh.framework.wechat.constants.WechatConstants
import com.github.caijh.framework.wechat.constants.WechatConstants.Companion.API_JS_API_SDK_LIST
import com.github.caijh.framework.wechat.enums.WechatType
import com.github.caijh.framework.wechat.exception.WechatApiException
import com.github.caijh.framework.wechat.model.*
import com.github.caijh.framework.wechat.service.WechatApis
import org.springframework.stereotype.Service
import java.io.File

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

    override fun getWechatJsSdkConfig(wechatApp: WechatApp, url: String): WechatJsSdkConfig {
        val ticket = this.getJsApiTicket(wechatApp)
        val jsSdkSignature =
            WechatJsSdkSignature(ticket, UuidUtils.uuid(), DateUtils.currentTimestamp().toInt(), url)
        return WechatJsSdkConfig(
            wechatApp.appId,
            jsSdkSignature.ticket,
            jsSdkSignature.url,
            jsSdkSignature.nonceStr,
            jsSdkSignature.timestamp,
            jsSdkSignature.signature,
            Strings.toListByComma(API_JS_API_SDK_LIST)
        )
    }

    override fun login(wechatApp: WechatApp, code: String): String {
        val url = when (wechatApp.type) {
            WechatType.MINI -> WechatConstants.API_URL + WechatConstants.API_MINI_AUTHORIZE + "?appid=" + wechatApp.appId + "&secret=" + wechatApp.secret + "&js_code=" + code + "&grant_type=authorization_code"
            WechatType.PUBLIC -> WechatConstants.API_URL + WechatConstants.API_PUBLIC_AUTHORIZE + "?appid=" + wechatApp.appId + "&secret=" + wechatApp.secret + "&code=" + code + "&grant_type=authorization_code"
            else ->
                throw IllegalArgumentException("wechat type not supported")

        }
        return doGet(url)
    }

    override fun getWechatUserInfo(wechatApp: WechatApp, openid: String): WechatUserInfo {
        val accessToken = getAccessToken(wechatApp)

        val url =
            "${WechatConstants.API_URL}${WechatConstants.API_USERINFO}?access_token=${accessToken}&openid=${wechatApp.appId}&lang=zh_CN"
        val respBody = this.doGet(url)
        return JSON.parseObject(respBody, WechatUserInfo::class.java)
    }

    override fun uploadMedia(wechatApp: WechatApp, type: String, file: File): WechatMedia {
        val accessToken = getAccessToken(wechatApp)
        val url =
            "${WechatConstants.API_URL}${WechatConstants.API_PUBLIC_MEDIA_UPLOAD}?access_token=${accessToken}&type=${type}"

        val upload = HttpClientUtils.upload(url, file)
        this.assertIsSuccess(upload.string())
        return JSON.parseObject(upload.string(), WechatMedia::class.java)
    }

    private fun doGet(url: String): String {
        val respBody = HttpClientUtils.get(url)
        assertIsSuccess(respBody)
        return respBody
    }

    private fun assertIsSuccess(respBody: String?) {
        val data = JSON.parseObject(respBody, WechatRespBody::class.java)
        if (!data.success()) {
            throw WechatApiException(data.errcode(), data.errmsg())
        }
    }
}
