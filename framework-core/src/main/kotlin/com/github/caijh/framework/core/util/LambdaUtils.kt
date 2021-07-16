package com.github.caijh.framework.core.util

import com.github.caijh.framework.core.exception.BizException
import com.github.caijh.framework.core.lambda.SFunction
import com.github.caijh.framework.core.lambda.SerializedLambda
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object LambdaUtils {
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
                SerializedLambda.resolve(func)
            } ?: throw BizException("Resolve SerializedLambda fail")

        FUNC_CACHE[clazz] = WeakReference(serializedLambda)
        return serializedLambda
    }


}
