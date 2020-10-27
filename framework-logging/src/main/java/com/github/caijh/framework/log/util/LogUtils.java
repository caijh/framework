package com.github.caijh.framework.log.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    private LogUtils() {

    }

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

}
