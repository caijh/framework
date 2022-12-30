package com.github.caijh.framework.core.model.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class IdsReqBody<T extends Serializable> implements ReqBody {

    private List<T> ids;

}
