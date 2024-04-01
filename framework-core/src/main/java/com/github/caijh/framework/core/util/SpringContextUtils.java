package com.github.caijh.framework.core.util;

import lombok.Getter;
import org.springframework.context.ApplicationContext;

/**
 * Util to get spring application context.
 */
public class SpringContextUtils {

    @Getter
    private static ApplicationContext applicationContext;

    private SpringContextUtils() {}

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtils.applicationContext = applicationContext;
    }

}
