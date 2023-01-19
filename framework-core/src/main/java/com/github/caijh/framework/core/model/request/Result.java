package com.github.caijh.framework.core.model.request;

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
public class Result<T> {

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

    public static Result<Void> of(String code) {
        return of(code, null);
    }

    public static Result<Void> of(String code, String message) {
        return new Result<Void>().setCode(code).setMessage(message);
    }

    public static <T> Result<T> of(String code, T data) {
        return new Result<T>().setCode(code).setData(data);
    }

    public static <T> Result<T> of(String code, String message, T data) {
        return new Result<T>().setCode(code).setMessage(message).setData(data);
    }

}
