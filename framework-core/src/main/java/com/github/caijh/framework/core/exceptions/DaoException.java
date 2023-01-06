package com.github.caijh.framework.core.exceptions;

/**
 * data access base exception.
 */
public abstract class DaoException extends RuntimeException {

    protected DaoException() {
    }

    protected DaoException(String message) {
        super(message);
    }

    protected DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    protected DaoException(Throwable cause) {
        super(cause);
    }

    protected DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
