package com.github.caijh.framework.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessToken {

    @JSONField(name = "access_token")
    @JsonProperty("access_token")
    private String token;

    @JSONField(name = "expires_in")
    @JsonProperty("expires_in")
    private Integer expiresIn;

    public String token() {
        return this.token;
    }

}
