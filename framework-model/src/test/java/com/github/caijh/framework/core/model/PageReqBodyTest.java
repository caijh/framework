package com.github.caijh.framework.core.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PageReqBodyTest {

    @Test
    void test() {
        PageReqBody reqBody = new PageReqBody();
        reqBody.setPageNo(1);
        reqBody.setPageSize(10);
        List<PageReqBody.Sort> sorts = new ArrayList<>();
        sorts.add(PageReqBody.Sort.builder().column("a_b_cc_dd").order("asc").toCamelCase(true).build());
        sorts.add(PageReqBody.Sort.builder().column("ab").order("asc").toCamelCase(true).build());
        sorts.add(PageReqBody.Sort.builder().column("acBc").order("asc").toCamelCase(true).build());
        reqBody.setSorts(sorts);
        Assertions.assertEquals("aBCcDd", sorts.get(0).getColumn());
    }

}
