package com.github.caijh.framework.core.util;

import com.github.caijh.framework.core.exceptions.ClassLoadException;
import org.springframework.util.ClassUtils;

public class ClassLoadUtils {

    private ClassLoadUtils() {
    }

    public static Class<?> toClassConfident(String name) {
        try {
            return Class.forName(name, false, ClassUtils.getDefaultClassLoader());
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException ex) {
                throw new ClassLoadException("找不到指定的class！请仅在明确确定会有 class 的时候，调用该方法", e);
            }
        }
    }

}
