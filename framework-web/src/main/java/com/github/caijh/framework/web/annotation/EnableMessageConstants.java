package com.github.caijh.framework.web.annotation;


public @interface EnableMessageConstants {

    boolean enable() default false;

    String propertiesLocation();

    String packageName();

    String className();

}
