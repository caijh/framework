package com.github.caijh.framework.core.exception;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BizException extends RuntimeException implements Serializable {

    /**
     * 业务异常代码，同时作为异常消息国际化的Key.
     */
    private String code;

    /**
     * 国际化参数.
     */
    private Object[] params;

    public BizException() {
        super();
    }

    public BizException(String code, Object... params) {
        super();
        this.code = code;
        this.params = params;
    }

    public BizException(Exception e) {
        super(e);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

}
