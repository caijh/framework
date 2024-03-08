package com.github.caijh.framework.core.exception;

/**
 * Service base exception.
 */
public abstract class ServiceException extends LocalizedException {

    public ServiceException(String code, Object[] params) {
        super(code, params);
    }

    public ServiceException(String code, Object[] params, Throwable cause) {
        super(code, params, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
