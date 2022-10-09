package com.example.instapic.Validations;

import com.example.instapic.Annotation.ValidEmail;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final  String EMAIL_PATTERN =   "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return (validaateEmail(email));
    }
    public boolean validaateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
