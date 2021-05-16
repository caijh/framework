package com.github.caijh.framework.wechat.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@XmlRootElement
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WxTextMessage extends BaseWxMessage {

    public static final String MESSAGE_TYPE = "text";
    @XmlElement(name = "Content")
    private String content;

    @Override
    public String type() {
        return WxTextMessage.MESSAGE_TYPE;
    }

}
