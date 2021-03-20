package com.github.caijh.framework.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WechatPublicLoginRespBody extends WechatLoginRespBody {

    @JSONField(name = "access_token")
    @JsonProperty(value = "access_token")
    private String accessToken;

    @JSONField(name = "expires_in")
    @JsonProperty(value = "expires_in")
    private Integer expiresIn;

    @JSONField(name = "refresh_token")
    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    private String openid;

    private String scope;


}
