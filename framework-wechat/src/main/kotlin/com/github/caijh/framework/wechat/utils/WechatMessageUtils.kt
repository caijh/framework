package com.github.caijh.framework.wechat.utils

import com.github.caijh.commons.util.Maps
import com.github.caijh.framework.core.exception.BizException
import com.github.caijh.framework.util.XmlUtils
import com.github.caijh.framework.wechat.model.*

object WechatMessageUtils {

    fun convert(xml: String): WxMessage {
        val maps = Maps.fromXml(xml)
        return when (maps.getOrDefault("MsgType", "unknown")) {
            WxTextMessage.MESSAGE_TYPE -> XmlUtils.fromXml(xml, WxTextMessage::class.java)
            WxImageMessage.MESSAGE_TYPE -> XmlUtils.fromXml(xml, WxImageMessage::class.java)
            WxVoiceMessage.MESSAGE_TYPE -> XmlUtils.fromXml(xml, WxVoiceMessage::class.java)
            WxNewsMessage.MESSAGE_TYPE -> XmlUtils.fromXml(xml, WxNewsMessage::class.java)
            WxEventMessage.MESSAGE_TYPE -> XmlUtils.fromXml(xml, WxEventMessage::class.java)
            else -> {
                throw BizException("message type not supported")
            }
        }
    }

}
