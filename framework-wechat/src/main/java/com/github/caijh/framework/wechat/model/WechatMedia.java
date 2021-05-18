package com.github.caijh.framework.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class WechatMedia {

    @JSONField(name = "media_id")
    private String mediaId;

    private String url;

}
