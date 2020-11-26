package com.github.caijh.framework.core.exception;

public class BizException extends RuntimeException {

    public BizException() {
        super();
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
