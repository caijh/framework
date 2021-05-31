package com.github.caijh.framework.core.utils

import com.github.caijh.framework.core.exception.BizException
import com.github.caijh.framework.core.lambda.SFunction
import java.lang.invoke.SerializedLambda
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object Lambda {
    /**
     * SerializedLambda 反序列化缓存
     */
    private val FUNC_CACHE: MutableMap<Class<*>, WeakReference<SerializedLambda>> = ConcurrentHashMap()

    @JvmStatic
    fun <T> resolve(func: SFunction<T, *>): SerializedLambda {
        val clazz: Class<*> = func.javaClass
        val serializedLambda = Optional.ofNullable(FUNC_CACHE[clazz])
            .map { obj: WeakReference<SerializedLambda> -> obj.get() }
            .orElseGet {
                getSerializedLambda(func)
            } ?: throw BizException("Resolve SerializedLambda fail")

        FUNC_CACHE[clazz] = WeakReference(serializedLambda)
        return serializedLambda
    }

    private fun <T> getSerializedLambda(func: SFunction<T, *>): SerializedLambda {
        val method = func.javaClass.getDeclaredMethod("writeReplace")
        method.isAccessible = true
        return method.invoke(func) as SerializedLambda
    }
}
