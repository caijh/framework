package com.github.caijh.framework.wechat.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@XmlRootElement(name = "xml")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WxTextMessage extends BaseWxMessage {

    public static final String MESSAGE_TYPE = "text";
    private static final long serialVersionUID = -4507919674484687317L;
    @XmlElement(name = "Content")
    private String content;

    public WxTextMessage(String fromUserName, String toUserName, String content) {
        super(fromUserName, toUserName);
        this.content = content;
    }

    @Override
    public String type() {
        return WxTextMessage.MESSAGE_TYPE;
    }

}
