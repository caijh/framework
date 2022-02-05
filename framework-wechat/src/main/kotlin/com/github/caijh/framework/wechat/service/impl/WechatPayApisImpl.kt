package com.github.caijh.framework.wechat.service.impl

import com.github.caijh.commons.util.HttpClient.post
import com.github.caijh.framework.util.XmlUtils
import com.github.caijh.framework.wechat.constants.WechatConstants.Companion.PAY_UNIFIEDORDER
import com.github.caijh.framework.wechat.model.WxPayOrderCreateReqBody
import com.github.caijh.framework.wechat.model.WxPayOrderCreateRespBody
import com.github.caijh.framework.wechat.service.WechatPayApis
import okhttp3.MediaType
import org.springframework.stereotype.Service

@Service
class WechatPayApisImpl : WechatPayApis {
    override fun createOrder(orderCreateReqBody: WxPayOrderCreateReqBody): WxPayOrderCreateRespBody {
        val xml = XmlUtils.toXml(orderCreateReqBody, "xml")
        val response = post(PAY_UNIFIEDORDER, null, MediaType.parse("application/xml; charset=UTF-8"), xml)
        return XmlUtils.fromXml(response, WxPayOrderCreateRespBody::class.java)
    }
}
