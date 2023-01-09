package com.github.caijh.framework.core.util;

import org.springframework.context.ApplicationContext;

/**
 * Util to get spring application context.
 */
public class SpringContextUtils {

    private static ApplicationContext applicationContext;

    private SpringContextUtils() {}

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtils.applicationContext = applicationContext;
    }

}
