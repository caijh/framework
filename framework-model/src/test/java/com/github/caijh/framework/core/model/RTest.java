package com.github.caijh.framework.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RTest {

    @Test
    void builder() {
        R<String> ok = R.of("ok", "");
        assertEquals("ok", ok.getMessage());

        R<Void> aNull = R.of("ok");
        assertEquals("ok", aNull.getMessage());
        assertNull(aNull.getData());
    }

}
