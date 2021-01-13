package com.github.caijh.framework.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.caijh.framework.util.ValidatorUtils;


public class PhoneTelValidator implements ConstraintValidator<CheckPhoneTel, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }

        return ValidatorUtils.isMobile(s) || ValidatorUtils.isTel(s);
    }

}
