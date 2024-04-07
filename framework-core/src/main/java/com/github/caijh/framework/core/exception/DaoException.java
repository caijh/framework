package com.github.caijh.framework.core.exception;

import org.springframework.lang.Nullable;

/**
 * data access base exception.
 */
public class DaoException extends LocalizedException {

    public DaoException(String code, Object[] params) {
        super(code, params, null, null);
    }

    public DaoException(String code, Object[] params, String defaultMessage) {
        super(code, params, defaultMessage, null);
    }

    public DaoException(String code, Object[] params, Throwable cause) {
        super(code, params, null, cause);
    }

    public DaoException(String code, Object[] params, @Nullable String defaultMessage, @Nullable Throwable cause) {
        super(code, params, defaultMessage, cause);
    }

}
