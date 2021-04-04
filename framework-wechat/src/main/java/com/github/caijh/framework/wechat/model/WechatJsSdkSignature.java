package com.github.caijh.framework.wechat.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.github.caijh.commons.util.HexUtils;
import com.github.caijh.framework.wechat.exception.WechatJsSdkSignatureException;

public class WechatJsSdkSignature {

    private final String ticket;

    private final String nonceStr;

    private final Integer timestamp;

    private final String url;

    private final String signature;

    public WechatJsSdkSignature(String ticket, String nonceStr, Integer timestamp, String url) {
        this.ticket = ticket;
        this.nonceStr = nonceStr;
        this.timestamp = timestamp;
        this.url = url;
        // 注意这里参数名必须全部小写，且必须有序
        String paramString = "jsapi_ticket=" + this.ticket
                + "&noncestr=" + this.nonceStr
                + "&timestamp=" + this.timestamp
                + "&url=" + this.url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(paramString.getBytes(StandardCharsets.UTF_8));
            this.signature = HexUtils.INSTANCE.byteArr2HexStr(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new WechatJsSdkSignatureException(e);
        }
    }

    public String getTicket() {
        return ticket;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public String getUrl() {
        return url;
    }

    public String getSignature() {
        return signature;
    }

}
