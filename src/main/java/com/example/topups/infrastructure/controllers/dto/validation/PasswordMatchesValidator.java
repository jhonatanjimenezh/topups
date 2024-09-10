package com.example.topups.infrastructure.controllers.dto.validation;

import com.example.topups.infrastructure.controllers.dto.request.UserRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRequestDTO> {

    @Override
    public boolean isValid(UserRequestDTO userRequestDTO, ConstraintValidatorContext context) {
        if (userRequestDTO.getPassword() == null || userRequestDTO.getConfirmPassword() == null) {
            return true;
        }

        if (!userRequestDTO.getPassword().equals(userRequestDTO.getConfirmPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La contraseña y la confirmación de la contraseña no coinciden")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
