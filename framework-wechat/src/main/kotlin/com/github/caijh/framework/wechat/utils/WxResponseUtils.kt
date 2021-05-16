package com.github.caijh.framework.wechat.utils

import com.github.caijh.commons.util.Maps
import com.github.caijh.framework.core.exception.BizException

object WxResponseUtils {
    private const val FAIL = "FAIL"
    private const val SUCCESS = "SUCCESS"

    fun processXmlResponse(xml: String): Map<String, Any> {
        val respBody: Map<String, Any> = Maps.fromXml(xml)

        val returnCode = respBody["return_code"]
            ?: throw BizException(String.format("No `return_code` in XML: %s", xml))
        if (!(FAIL == returnCode || SUCCESS == (returnCode))) {
            throw BizException(String.format("returnCode value %s is invalid in XML: %s", returnCode, xml))
        }

        return respBody
    }
}
