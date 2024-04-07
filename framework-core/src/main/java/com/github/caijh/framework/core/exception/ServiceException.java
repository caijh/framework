package com.github.caijh.framework.core.exception;

import org.springframework.lang.Nullable;

/**
 * Service base exception.
 */
public class ServiceException extends LocalizedException {

    public ServiceException(String code, Object[] params) {
        super(code, params, null, null);
    }

    public ServiceException(String code, Object[] params, String defaultMessage) {
        super(code, params, defaultMessage, null);
    }

    public ServiceException(String code, Object[] params, Throwable cause) {
        super(code, params, null, cause);
    }

    public ServiceException(String code, Object[] params, @Nullable String defaultMessage, @Nullable Throwable cause) {
        super(code, params, defaultMessage, cause);
    }

}
