package com.github.caijh.framework.core.lock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Locking annotation enable to get lock before execute method.
 * @author caijh
 */
@Target(value = ElementType.METHOD)
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Locking {
    /**
     * key.
     * @return key
     */
    String key() default "";

    /**
     * 过期时间，单位ms, -1表示不过期.
     * @return 过期时间，单位ms, -1表示不过期.
     */
    int expired() default -1;
}
