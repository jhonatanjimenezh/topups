package com.example.topups.infrastructure.controllers.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class AuthRequestDTO {

    private static final Logger logger = LoggerFactory.getLogger(AuthRequestDTO.class);

    @NotNull(message = "El nombre de usuario es obligatorio.")
    @NotBlank(message = "El campo de nombre de usuario no puede estar vacío.")
    private String username;

    @NotNull(message = "La contraseña es obligatoria.")
    @NotBlank(message = "El campo de contraseña no puede estar vacío.")
    private String password;

    public void logRequest() {
        logger.info("Solicitud de autenticación con username: {}", username);
    }
}
