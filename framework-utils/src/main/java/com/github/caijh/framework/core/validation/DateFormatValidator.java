package com.github.caijh.framework.core.validation;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * help to check date string format.
 */
public class DateFormatValidator implements ConstraintValidator<CheckDateFormat, String> {

    private CheckDateFormat checkDateFormat;

    @Override
    public void initialize(CheckDateFormat constraintAnnotation) {
        this.checkDateFormat = constraintAnnotation;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true; // null不需要校验
        }

        Pattern pattern = Pattern.compile(checkDateFormat.format());

        return pattern.matcher(s).matches();
    }

}
