package com.github.caijh.framework.wechat.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.github.caijh.commons.util.DateUtils;
import lombok.Data;

@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
@Data
public abstract class BaseWxMessage implements WxMessage {

    private static final long serialVersionUID = -3609113555725837052L;
    @XmlElement(name = "ToUserName")
    protected String toUserName;

    @XmlElement(name = "FromUserName")
    protected String fromUserName;

    @XmlElement(name = "CreateTime")
    protected Long createTime;

    @XmlElement(name = "MsgType")
    protected String msgType;

    BaseWxMessage() {
        this.fromUserName = "";
        this.toUserName = "";
        this.createTime = DateUtils.currentTimestamp();
    }

    BaseWxMessage(String fromUserName, String toUserName) {
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
        this.createTime = DateUtils.currentTimestamp();
    }

    public String getMsgType() {
        return this.type();
    }

}
