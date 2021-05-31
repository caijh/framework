package com.github.caijh.framework.core.utils

import com.github.caijh.framework.core.exception.ReflectionException
import java.util.*

object PropertyResolver {

    @JvmStatic
    fun methodToProperty(name: String): String {
        var propertyName = name
        propertyName = if (propertyName.startsWith("is")) {
            propertyName.substring(2)
        } else if (propertyName.startsWith("get") || propertyName.startsWith("set")) {
            propertyName.substring(3)
        } else {
            throw ReflectionException("Error parsing property name '$propertyName'.  Didn't start with 'is', 'get' or 'set'.")
        }
        if (propertyName.length == 1 || propertyName.length > 1 && !Character.isUpperCase(propertyName[1])) {
            propertyName = propertyName.substring(0, 1).toLowerCase(Locale.ENGLISH) + propertyName.substring(1)
        }
        return propertyName
    }

    @JvmStatic
    fun isProperty(name: String): Boolean {
        return isGetter(name) || isSetter(name)
    }

    @JvmStatic
    fun isGetter(name: String): Boolean {
        return name.startsWith("get") && name.length > 3 || name.startsWith("is") && name.length > 2
    }

    @JvmStatic
    fun isSetter(name: String): Boolean {
        return name.startsWith("set") && name.length > 3
    }
}
