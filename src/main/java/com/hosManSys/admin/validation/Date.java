package com.hosManSys.admin.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Date {
    
    public String message() default "Invalid Date Entered.";
    
    public Class<?>[] groups() default {};   
    
    public Class<? extends Payload>[] payload() default {};
}