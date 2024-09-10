package com.example.topups.application.usecases;

import com.example.topups.application.ports.UserUseCase;
import com.example.topups.domain.entities.Login;
import com.example.topups.domain.entities.User;
import com.example.topups.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private static final Logger logger = LoggerFactory.getLogger(UserUseCaseImpl.class);
    private final UserService userService;

    @Override
    public void registerUser(User user, Login login) {
        try {
            logger.info("Iniciando el registro de usuario. Datos de usuario: {}, Datos de login: {}", user, login);
            userService.registerUser(user, login);
            logger.info("Usuario registrado exitosamente. Email: {}, Username: {}", user.getEmail(), login.getUsername());
        } catch (Exception e) {
            logger.error("Error al registrar el usuario con Email: {} y Username: {}. Detalle: {}", user.getEmail(), login.getUsername(), e.getMessage(), e);
            throw e;
        }
    }
}
