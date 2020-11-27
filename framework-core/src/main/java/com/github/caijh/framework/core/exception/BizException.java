package com.github.caijh.framework.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
        super();
        this.code = null;
        this.params = null;
    }

    public BizException(String code, Object... params) {
        super();
        this.code = code;
        this.params = params;
    }

    public BizException(Exception e) {
        super(e);
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

}
