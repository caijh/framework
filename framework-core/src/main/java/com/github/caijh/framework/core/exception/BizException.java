package com.github.caijh.framework.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizException extends RuntimeException {

    /**
     * 业务异常代码，同时作为异常消息国际化的Key.
     */
    private final String code;

    /**
     * 国际化参数.
     */
    private final transient Object[] params;

    public BizException() {
        this.code = null;
        this.params = null;
    }

    public BizException(Throwable cause) {
        super(cause);
        this.code = null;
        this.params = null;
    }

    public BizException(String message) {
        super(message);
        this.code = null;
        this.params = null;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.code = null;
        this.params = null;
    }

    private BizException(String code, Object... params) {
        this.code = code;
        this.params = params;
    }

    public static BizException of(String code, Object... params) {
        return new BizException(code, params);
    }

}
