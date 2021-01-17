package com.github.caijh.framework.web.exception;

public class MessageConstantsException extends RuntimeException {

    public MessageConstantsException(Throwable cause) {
        super(cause);
    }

    public MessageConstantsException(String message) {
        super(message);
    }

}
