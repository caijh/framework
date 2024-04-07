package com.github.caijh.framework.core.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Getter
public abstract class CodedException extends RuntimeException {

    /**
     * 业务异常代码
     */
    private final String code;

    protected CodedException(@NonNull String code, @Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
