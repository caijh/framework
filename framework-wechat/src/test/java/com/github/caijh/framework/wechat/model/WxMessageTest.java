package com.github.caijh.framework.wechat.model;

import com.github.caijh.framework.util.XmlUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WxMessageTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test() {
        WxMessage message = new WxTextMessage("a", "b", "hello");
        String xml = XmlUtils.toXml(message);
        System.out.println(xml);
    }

}
