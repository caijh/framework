package com.github.caijh.framework.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WechatUserInfo extends WechatRespBody {

    private static final long serialVersionUID = 1L;

    private String openId;

    private String nickName;

    private String sex;

    private String province;

    private String city;

    private String country;

    @JSONField(name = "headimgurl")
    @JsonProperty(value = "headimgurl")
    private String headImgUrl;

    private String privilege;

    private String unionId;

}
