package com.github.caijh.framework.data.mybatis.utils;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PageUtils {

    private PageUtils() {
    }

    public static <T, R> Page<R> newPage(Page<T> page, List<R> records) {
        Page<R> newPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        newPage.setRecords(records);
        return newPage;
    }

}
