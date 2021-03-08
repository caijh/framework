package com.github.caijh.framework.wechat.enums

import com.github.caijh.framework.core.enums.IndexEnum

enum class WechatType(private val index: Int) : IndexEnum {
    UNKNOWN(-1),

    /**
     * 公众号
     */
    PUBLIC(0),

    /**
     * 小程序
     */
    MINI(1);

    override fun getIndex(): Int {
        return index
    }
}
