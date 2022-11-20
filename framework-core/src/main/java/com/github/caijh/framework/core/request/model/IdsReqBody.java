package com.github.caijh.framework.core.request.model;

import java.util.List;

import lombok.Data;

@Data
public class IdsReqBody<T> implements ReqBody {

    private List<T> ids;

}
