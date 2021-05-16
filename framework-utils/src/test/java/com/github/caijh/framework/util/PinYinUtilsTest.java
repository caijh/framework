package com.github.caijh.framework.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PinYinUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void toPinyin() {

        String china = "中国";
        String pinyin = PinYinUtils.toPinyin(china);
        Assertions.assertEquals("zhongguo", pinyin);

    }

}
