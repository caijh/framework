package com.github.caijh.framework.core.model;

import lombok.Data;

@Data
public class R<T> {

    private String message;
    private T data;

    private R() {
    }

    public static <T> R<T> of(String message, T data) {
        R<T> result = new R<>();
        result.message = message;
        result.data = data;
        return result;
    }

    public static R<Void> of(String message) {
        R<Void> result = new R<>();
        result.message = message;
        return result;
    }

}
