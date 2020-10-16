package com.github.caijh.framework.core.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommonStatusTest {

    @Test
    void getIndex() {
        CommonStatus inUse = CommonStatus.IN_USE;
        CommonStatus deleted = CommonStatus.DELETED;

        Assertions.assertEquals(0, inUse.getIndex());
        Assertions.assertEquals(1, deleted.getIndex());
    }

    @Test
    void valueOf() {
        CommonStatus commonStatus = IndexEnum.valueOf(0, CommonStatus.class);
        Assertions.assertNotNull(commonStatus);
    }

}
