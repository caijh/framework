package com.github.caijh.framework.wechat.model

import com.github.caijh.framework.wechat.enums.WechatAppType

data class WechatApp(var appId: String, var secret: String, var type: WechatAppType = WechatAppType.UNKNOWN)
