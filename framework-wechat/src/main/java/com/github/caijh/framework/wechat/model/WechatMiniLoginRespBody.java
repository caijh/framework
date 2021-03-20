package com.github.caijh.framework.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WechatMiniLoginRespBody extends WechatLoginRespBody {

    @JSONField(name = "session_key")
    @JsonProperty(value = "session_key")
    private String sessionKey;

    @JSONField(name = "expires_in")
    @JsonProperty(value = "expires_in")
    private Integer expiresIn;

    private String openid;

    private String unionid;

}
