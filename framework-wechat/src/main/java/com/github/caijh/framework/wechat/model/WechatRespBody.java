package com.github.caijh.framework.wechat.model;

import lombok.Data;

@Data
public class WechatRespBody {

    private Integer errcode = 0;
    private String errmsg;

    public boolean success() {
        return errcode == 0;
    }

    public Integer errcode() {
        return this.errcode;
    }

    public String errmsg() {
        return this.errmsg;
    }

}
