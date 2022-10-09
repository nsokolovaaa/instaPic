package com.example.instapic.Annotation;

import com.example.instapic.Validations.PasswordMatchesValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "Password don't matches";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
