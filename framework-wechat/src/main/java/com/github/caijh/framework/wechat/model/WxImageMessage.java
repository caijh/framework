package com.github.caijh.framework.wechat.model;

import java.io.Serializable;
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
public class WxImageMessage extends BaseWxMessage {

    public static final String MESSAGE_TYPE = "image";
    private static final long serialVersionUID = -6983876301808965569L;

    @XmlElement(name = "Image")
    private Image image;

    @Override
    public String type() {
        return WxImageMessage.MESSAGE_TYPE;
    }

    public WxImageMessage(String fromUserName, String toUserName, Image image) {
        super(fromUserName, toUserName);
        this.image = image;
    }

    @Data
    @Accessors(chain = true)
    public static class Image implements Serializable {

        private static final long serialVersionUID = -2834797534188224504L;
        @XmlElement(name = "MediaId")
        private String mediaId;

    }

}
