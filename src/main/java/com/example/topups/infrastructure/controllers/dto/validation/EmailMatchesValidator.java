package com.example.topups.infrastructure.controllers.dto.validation;

import com.example.topups.infrastructure.controllers.dto.request.UserRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class EmailMatchesValidator implements ConstraintValidator<EmailMatches, UserRequestDTO> {

    @Override
    public boolean isValid(UserRequestDTO userRequestDTO, ConstraintValidatorContext context) {
        if (userRequestDTO.getEmail() == null || userRequestDTO.getConfirmEmail() == null) {
            return true;
        }

        if (!userRequestDTO.getEmail().equals(userRequestDTO.getConfirmEmail())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El correo electrónico y la confirmación del correo electrónico no coinciden")
                    .addPropertyNode("confirmEmail")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
