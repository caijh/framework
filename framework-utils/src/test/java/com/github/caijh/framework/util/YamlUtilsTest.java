package com.github.caijh.framework.util;

import com.alibaba.fastjson.JSON;
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
    void toJsonString() {
        String yaml = "a: 1\n b:2";
        String json = YamlUtils.toJsonString(yaml);

        assertEquals(1, JSON.parseObject(json).getInteger("a"));

    }

    @Test
    void toYamlString() {
        JSONObject obj = new JSONObject().fluentPut("a", 1).fluentPut("b", 2);
        String originJson = obj.toJSONString();
        String yaml = YamlUtils.toYamlString(originJson);
        String json = YamlUtils.toJsonString(yaml);

        assertEquals(json, originJson);
    }

}