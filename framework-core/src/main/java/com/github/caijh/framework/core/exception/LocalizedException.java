package com.github.caijh.framework.core.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Getter
public class LocalizedException extends CodedException {

    /**
     * 国际化参数.
     */
    private final transient Object[] params;

    public LocalizedException(@NonNull String code, Object[] params, @Nullable String defaultMessage, @Nullable Throwable cause) {
        super(code, defaultMessage, cause);
        this.params = params;
    }

}
