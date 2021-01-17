package com.github.caijh.framework.core.annotation;

public @interface EnableMessageConstants {

    boolean enable() default false;

    String propertiesLocation();

    String packageName();

    String className();

}
