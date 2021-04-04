package com.github.caijh.framework.wechat.model

import lombok.experimental.Accessors

@Accessors(chain = true)
data class WechatJsSdkConfig(
    val appId: String,
    val jsApiTicket: String,
    val url: String,
    val nonceStr: String,
    val timestamp: Int = 0,
    val signature: String,
    val jsApiList: List<String>
)
