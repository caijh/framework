package com.github.caijh.framework.wechat.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class WechatRespBody implements Serializable {

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
