package com.github.caijh.framework.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {

    @AliasFor("times")
    int value() default 3;

    @AliasFor("value")
    int times() default 3;

    int sleep() default 1000;

}
