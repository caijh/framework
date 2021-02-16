package com.github.caijh.framework.log.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use LogUtils to get Logger object, keep all use the same LoggerFactory.
 *
 * @author caijh
 */
public class LogUtils {

    private LogUtils() {

    }

    /**
     * get the logger of name.
     *
     * @param name log name
     * @return Logger
     */
    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    /**
     * get logger of the class.
     *
     * @param clazz class name
     * @return Logger
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

}
