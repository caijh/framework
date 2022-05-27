package com.github.caijh.framework.wechat.utils

import com.github.caijh.commons.util.Maps
import com.github.caijh.framework.core.service.exceptions.BizException
import com.github.caijh.framework.wechat.model.ResponseWechat.FAIL
import com.github.caijh.framework.wechat.model.ResponseWechat.SUCCESS

object WxResponseUtils {

    fun processXmlResponse(xml: String): Map<String, Any> {
        val respBody: Map<String, Any> = Maps.fromXml(xml)

        val returnCode = respBody["return_code"]
            ?: throw BizException(
                String.format(
                    "No `return_code` in XML: %s",
                    xml
                )
            )
        if (!(FAIL == returnCode || SUCCESS == (returnCode))) {
            throw BizException(
                String.format(
                    "returnCode value %s is invalid in XML: %s",
                    returnCode,
                    xml
                )
            )
        }

        return respBody
    }
}
