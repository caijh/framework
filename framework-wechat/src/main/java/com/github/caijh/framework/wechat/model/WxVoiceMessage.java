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
public class WxVoiceMessage extends BaseWxMessage {

    public static final String MESSAGE_TYPE = "voice";

    @XmlElement(name = "Voice")
    private Voice voice;

    @Override
    public String type() {
        return WxVoiceMessage.MESSAGE_TYPE;
    }

    @Data
    public static class Voice {

        @XmlElement(name = "MediaId")
        private String mediaId;

    }

}
