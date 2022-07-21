package com.github.caijh.framework.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorUtilsTest {

    @Test
    void isNumeric() {
        assertTrue(ValidatorUtils.isNumeric("9.1111"));
    }

    @Test
    void isUsername() {
        assertTrue(ValidatorUtils.isUsername("caijunhui"));
    }

    @Test
    void isMobile() {
        assertTrue(ValidatorUtils.isMobile("18928711111"));
    }

    @Test
    void isEmail() {
        assertTrue(ValidatorUtils.isEmail("caiqizhe@gmail.com"));
    }

    @Test
    void isChinese() {
        assertTrue(ValidatorUtils.isChinese("中国"));
        assertFalse(ValidatorUtils.isChinese("cai"));
    }

    @Test
    void isIDCard() {
        assertFalse(ValidatorUtils.isIdCard("440582"));
    }

    @Test
    void isUrl() {
        assertTrue(ValidatorUtils.isUrl("http://www.baidu.com"));
    }

    @Test
    void isIpAddr() {
        assertTrue(ValidatorUtils.isIpAddr("127.0.0.1"));
    }

    @Test
    void isTel() {
        assertTrue(ValidatorUtils.isTel("020-88888888"));
    }

}
