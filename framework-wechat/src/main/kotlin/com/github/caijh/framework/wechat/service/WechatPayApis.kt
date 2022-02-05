package com.github.caijh.framework.wechat.service

import com.github.caijh.framework.wechat.model.WxPayOrderCreateReqBody
import com.github.caijh.framework.wechat.model.WxPayOrderCreateRespBody

interface WechatPayApis {

    fun createOrder(orderCreateReqBody: WxPayOrderCreateReqBody): WxPayOrderCreateRespBody

}
