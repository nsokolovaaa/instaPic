package com.example.instapic.Validations;

import com.example.instapic.Annotation.PasswordMatches;
import com.example.instapic.Payload.Responce.Request.SignUpRequest;
import lombok.Data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        SignUpRequest userSignUp = (SignUpRequest) obj;
        return userSignUp.getPassword().equals(userSignUp.getConfirmPassword());
    }
}
