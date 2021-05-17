package com.github.caijh.framework.wechat.model;

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
public class WxEventMessage extends BaseWxMessage {

    public static final String MESSAGE_TYPE = "event";
    private static final long serialVersionUID = 2144123760513271002L;

    /**
     * 消息事件
     */
    private String event;

    /**
     * 事件key
     */
    private String eventKey;

    public WxEventMessage(String fromUserName, String toUserName, String event, String eventKey) {
        super(fromUserName, toUserName);
        this.event = event;
        this.eventKey = eventKey;
    }

    @Override
    public String type() {
        return WxEventMessage.MESSAGE_TYPE;
    }

}
