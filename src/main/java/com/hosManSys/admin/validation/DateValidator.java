package com.hosManSys.admin.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<Date, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches("^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$", value);
    }

}
