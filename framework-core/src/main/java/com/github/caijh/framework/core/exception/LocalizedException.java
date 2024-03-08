package com.github.caijh.framework.core.exception;

import lombok.Getter;

@Getter
public class LocalizedException extends RuntimeException {

    /**
     * 业务异常代码，同时作为异常消息国际化的Key.
     */
    private final String code;

    /**
     * 国际化参数.
     */
    private final transient Object[] params;

    public LocalizedException(String code, Object[] params) {
        this.code = code;
        this.params = params;
    }

    public LocalizedException(String code, Object[] params, Throwable cause) {
        super(cause);
        this.code = code;
        this.params = params;
    }

    public LocalizedException(String message) {
        super(message);
        this.code = null;
        this.params = null;
    }

    public LocalizedException(String message, Throwable cause) {
        super(message, cause);
        this.code = null;
        this.params = null;
    }

}
