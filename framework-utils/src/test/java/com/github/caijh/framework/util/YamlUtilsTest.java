package com.github.caijh.framework.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YamlUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void toYamlString() {
        JSONObject obj = new JSONObject().fluentPut("a", 1).fluentPut("b", 2);
        String originJson = obj.toJSONString();
        String yaml = YamlUtils.toYamlString(originJson);
        System.out.println(yaml);
        String json = YamlUtils.toJsonString(yaml);

        assertEquals(json, originJson);
    }

}
