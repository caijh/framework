package com.github.caijh.framework.wechat.model

import com.alibaba.fastjson.annotation.JSONField
import com.fasterxml.jackson.annotation.JsonProperty

class WechatMiniLoginRespBody : WechatLoginRespBody() {
    @JSONField(name = "session_key")
    @JsonProperty("session_key")
    val sessionKey: String? = null

    val openid: String? = null

    val unionid: String? = null
}
