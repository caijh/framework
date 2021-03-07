package com.github.caijh.framework.wechat.exception

class WechatApiException(var errcode: Int, errmsg: String) : RuntimeException(errmsg)
