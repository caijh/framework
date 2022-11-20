package com.github.caijh.framework.core.request.model;

import java.util.*;

import lombok.Data;

@Data
public class IdsReqBody<T> implements ReqBody {

    private List<T> ids;

}
