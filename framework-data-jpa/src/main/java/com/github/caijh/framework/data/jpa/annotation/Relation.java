package com.github.caijh.framework.data.jpa.annotation;

import com.github.caijh.framework.data.jpa.JoinType;

public @interface Relation {

    Class<?> base();

    Class<?> reference();

    String on() default "";

    JoinType joinType() default JoinType.INNER;

}
