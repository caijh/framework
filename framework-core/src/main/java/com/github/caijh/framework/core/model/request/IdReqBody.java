package com.github.caijh.framework.core.model.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 带id请求体.
 *
 * @param <T> type of id.
 */
@Data
public class IdReqBody<T extends Serializable> implements ReqBody {

    /**
     * id
     */
    private T id;

}
