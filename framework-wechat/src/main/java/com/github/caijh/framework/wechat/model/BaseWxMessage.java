package com.github.caijh.framework.wechat.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.github.caijh.commons.util.DateUtils;
import lombok.Data;
import lombok.experimental.Accessors;

@XmlRootElement(name = "xml")
@Data
@Accessors(chain = true)
public abstract class BaseWxMessage implements WxMessage {

    @XmlElement(name = "ToUserName")
    protected String toUserName;

    @XmlElement(name = "FromUserName")
    protected String fromUserName;

    @XmlElement(name = "CreateTime")
    protected Long createTime = DateUtils.currentTimestamp();

    @XmlElement(name = "MsgType")
    protected String msgType;

}
