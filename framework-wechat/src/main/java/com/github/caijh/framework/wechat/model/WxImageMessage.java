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
public class WxImageMessage extends BaseWxMessage {

    public static final String MESSAGE_TYPE = "image";

    @XmlElement(name = "Image")
    private Image image;

    @Override
    public String type() {
        return WxImageMessage.MESSAGE_TYPE;
    }

    @Data
    @Accessors(chain = true)
    public static class Image {

        @XmlElement(name = "MediaId")
        private String mediaId;

    }

}
