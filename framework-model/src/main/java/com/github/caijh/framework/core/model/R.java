package com.github.caijh.framework.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 响应消息.
 *
 * @param <T> hold数据对象类型
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> {

    /**
     * 响应编码
     */
    private String code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 携带的数据
     */
    private T data;

    public static R<Void> of(String code) {
        return of(code, null);
    }

    public static R<Void> of(String code, String message) {
        return new R<Void>().setCode(code).setMessage(message);
    }

    public static <T> R<T> of(String code, String message, T data) {
        return new R<T>().setCode(code).setMessage(message).setData(data);
    }

}
