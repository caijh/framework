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
public class WxVoiceMessage extends BaseWxMessage {

    public static final String MESSAGE_TYPE = "voice";
    private static final long serialVersionUID = 5124558249592055699L;

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

    public WxVoiceMessage(String fromUserName, String toUserName, Voice voice) {
        super(fromUserName, toUserName);
        this.voice = voice;
    }

}
