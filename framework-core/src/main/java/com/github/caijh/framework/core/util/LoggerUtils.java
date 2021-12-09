package com.github.caijh.framework.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use LoggerUtils to get Logger object, keep all use the same LoggerFactory.
 *
 * @author caijh
 */
public class LoggerUtils {

    private LoggerUtils() {}

    /**
     * get the (logger) of name.
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
