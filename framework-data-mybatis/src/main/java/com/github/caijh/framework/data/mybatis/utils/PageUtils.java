package com.github.caijh.framework.data.mybatis.utils;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PageUtils {

    private PageUtils() {
    }

    public static <T, R> Page<R> newPage(Page<T> oldPage, List<R> records) {
        Page<R> page = new Page<>(oldPage.getCurrent(), oldPage.getSize(), oldPage.getTotal(), oldPage.isSearchCount());
        page.setRecords(records);
        return page;
    }

}
