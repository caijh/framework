package com.github.caijh.framework.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class AccessToken {

    @JSONField(name = "access_token")
    private String token;

    @JSONField(name = "expires_in")
    private Integer expiresIn;

}
