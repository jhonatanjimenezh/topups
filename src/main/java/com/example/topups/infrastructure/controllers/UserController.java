package com.example.topups.infrastructure.controllers;

import com.example.topups.domain.entities.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.topups.application.ports.UserUseCase;
import com.example.topups.domain.entities.User;
import com.example.topups.infrastructure.controllers.dto.ResponseDTO;
import com.example.topups.infrastructure.controllers.dto.request.UserRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserUseCase userUseCase;

    @PostMapping
    public ResponseEntity<ResponseDTO<Void>> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        logger.info("Iniciando el proceso de registro de usuario para: {}", userRequestDTO.getUsername());
        try {
            User user = userRequestDTO.toModel();
            Login login = userRequestDTO.toModelLogin();

            logger.debug("Usuario: {}, Login: {} - Datos validados correctamente. Procediendo a guardar en la base de datos.",
                    user.getEmail(), login.getUsername());

            userUseCase.registerUser(user, login);

            logger.info("Usuario {} registrado exitosamente con el ID: {}", user.getEmail(), user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(
                    true, HttpStatus.CREATED.value(), "Usuario registrado exitosamente", null
            ));
        } catch (Exception ex) {
            logger.error("Error al registrar el usuario {}: {}", userRequestDTO.getEmail(), ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(new ResponseDTO<>(
                    false, HttpStatus.BAD_REQUEST.value(), "Error al registrar el usuario: " + ex.getMessage(), null
            ));
        }
    }
}
