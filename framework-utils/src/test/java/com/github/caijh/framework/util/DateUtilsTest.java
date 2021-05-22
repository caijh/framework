package com.github.caijh.framework.util;

import java.util.Date;

import com.github.caijh.commons.util.DateUtils;
import com.github.caijh.framework.core.constants.DateFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

    @Test
    void test() {
        final String dateStr1 = "2021-04-18 00:00:00.000";
        Date date = DateUtils.parse(dateStr1, DateFormat.YYYY_MM_DD_HH_MM_SS_SSS);
        final String dateStr2 = DateUtils.format(date, DateFormat.YYYY_MM_DD_HH_MM_SS_SSS);
        Assertions.assertEquals(dateStr1, dateStr2);
    }

}
