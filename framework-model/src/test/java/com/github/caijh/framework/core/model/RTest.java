package com.github.caijh.framework.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RTest {

    @Test
    void builder() {
        R<Void> ok = R.of("ok");
        assertEquals("ok", ok.getCode());

        R<Void> aNull = R.of("ok", "everything is ok");
        assertEquals("everything is ok", aNull.getMessage());
        assertNull(aNull.getData());
    }

}
