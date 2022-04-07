package com.github.caijh.framework.data.jpa.annotation;

/**
 * 表关联视图
 */
public @interface TableView {

    Class<?> base();

    Relation[] value();

}
