package com.github.caijh.framework.wechat.model

data class WechatJsSdkConfig(
    val url: String? = null,
    val appId: String? = null,
    val jsApiTicket: String? = null,
    val nonceStr: String? = null,
    val timestamp: Int = 0,
    val signature: String? = null,
    val jsApiList: List<String>? = null,
)
