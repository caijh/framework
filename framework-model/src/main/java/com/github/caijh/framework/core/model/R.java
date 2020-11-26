package com.github.caijh.framework.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> {

    private String code;
    private String message;
    private T data;

    private R() {
    }

    public static R<Void> of(String code) {
        return of(code, null);
    }

    public static R<Void> of(String code, String message) {
        return of(code, message, null);
    }

    public static <T> R<T> of(String code, String message, T data) {
        R<T> result = new R<>();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }

}
