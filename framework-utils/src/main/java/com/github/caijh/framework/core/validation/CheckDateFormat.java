package com.github.caijh.framework.core.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.github.caijh.framework.util.constant.Constants;

@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckDateFormat {

    String message() default "请输入正确的日期格式";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String format() default Constants.Date.YYYY_MM_DD_HH_MM_SS;

}
