package com.github.caijh.framework.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RTest {

    @Test
    void builder() {
        R<Void> ok = new R<Void>().setCode("ok").setMessage("everything is ok");
        assertEquals("ok", ok.getCode());
        assertEquals("everything is ok", ok.getMessage());
        assertNull(ok.getData());
    }

}
