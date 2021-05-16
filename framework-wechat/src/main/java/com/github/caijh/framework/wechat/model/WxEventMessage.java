package com.github.caijh.framework.wechat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WxEventMessage extends BaseWxMessage {

    public static final String MESSAGE_TYPE = "event";

    /**
     * 消息事件
     */
    private String event;

    /**
     * 事件key
     */
    private String eventKey;

    @Override
    public String type() {
        return WxEventMessage.MESSAGE_TYPE;
    }

}
