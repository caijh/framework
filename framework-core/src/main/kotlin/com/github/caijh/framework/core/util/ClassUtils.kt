package com.github.caijh.framework.core.util

import com.github.caijh.framework.core.exceptions.BizException

object ClassUtils {
    @JvmStatic
    fun toClassConfident(name: String?): Class<*> {
        return try {
            Class.forName(name, false, getDefaultClassLoader())
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

    @JvmStatic
    fun getDefaultClassLoader(): ClassLoader? {
        var cl: ClassLoader? = null
        try {
            cl = Thread.currentThread().contextClassLoader
        } catch (ex: Throwable) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils::class.java.classLoader
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader()
                } catch (ex: Throwable) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl
    }
}
