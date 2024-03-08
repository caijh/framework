package com.github.caijh.framework.core.exception;

/**
 * Service base exception.
 */
public abstract class ServiceException extends LocalizedException {

    protected ServiceException(String code, Object[] params) {
        super(code, params);
    }

    protected ServiceException(String code, Object[] params, Throwable cause) {
        super(code, params, cause);
    }

    protected ServiceException(String message) {
        super(message);
    }

    protected ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
