package com.github.caijh.framework.core.util

import com.github.caijh.framework.core.exceptions.BizException

object ClassUtils {
    @JvmStatic
    fun toClassConfident(name: String?): Class<*> {
        return try {
            Class.forName(name, false, org.springframework.util.ClassUtils.getDefaultClassLoader())
        } catch (e: ClassNotFoundException) {
            try {
                Class.forName(name)
            } catch (ex: ClassNotFoundException) {
                throw BizException(
                    "找不到指定的class！请仅在明确确定会有 class 的时候，调用该方法",
                    e
                )
            }
        }
    }

}
