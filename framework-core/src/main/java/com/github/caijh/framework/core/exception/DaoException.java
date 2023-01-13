package com.github.caijh.framework.core.exception;

/**
 * data access base exception.
 */
public abstract class DaoException extends LocalizedException {

    protected DaoException(String code, Object[] params) {
        super(code, params);
    }

    protected DaoException(String code, Object[] params, Throwable cause) {
        super(code, params, cause);
    }

    protected DaoException(String message) {
        super(message);
    }

    protected DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    protected DaoException(Throwable cause) {
        super(null, null, cause);
    }

}
