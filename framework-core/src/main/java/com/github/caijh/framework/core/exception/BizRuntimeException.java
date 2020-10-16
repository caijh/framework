package com.github.caijh.framework.core.exception;

public class BizRuntimeException extends RuntimeException {

    public BizRuntimeException() {
        super();
    }

    public BizRuntimeException(Exception e) {
        super(e);
    }

    public BizRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
