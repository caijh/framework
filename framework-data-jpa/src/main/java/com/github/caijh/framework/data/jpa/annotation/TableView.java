package com.github.caijh.framework.data.jpa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表关联视图
 */
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableView {

    /**
     * 视图名称
     *
     * @return 视图名称
     */
    String name() default "";

    String version() default "1.0.0";

    Class<?> base();

    Relation[] relations();

}
