package com.github.caijh.framework.data.jpa.annotation;

import com.github.caijh.framework.data.jpa.JoinType;

public @interface Relation {

    Class<?> base() default RelationBaseNull.class;

    Class<?> reference();

    String on() default "";

    JoinType joinType() default JoinType.INNER;

    public interface RelationBaseNull {

    }

}
