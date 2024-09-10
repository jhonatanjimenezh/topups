package com.example.topups.infrastructure.controllers.dto.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailMatchesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailMatches {
    String message() default "El correo electrónico y la confirmación del correo electrónico no coinciden";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
