package com.github.caijh.framework.core.exceptions;

/**
 * service base exception.
 */
public abstract class ServiceException extends RuntimeException {

    protected ServiceException() {
    }

    protected ServiceException(String message) {
        super(message);
    }

    protected ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
