package com.github.caijh.framework.wechat.constants

class WechatConstants {
    companion object {
        const val API_URL = "https://api.weixin.qq.com/"
        const val API_TOKEN_URI = "cgi-bin/token?grant_type=client_credential"
        const val API_JS_API_TICKET = "cgi-bin/ticket/getticket"

        const val API_PUBLIC_AUTHORIZE = "sns/oauth2/access_token"
        const val API_MINI_AUTHORIZE = "sns/jscode2session"
    }
}
