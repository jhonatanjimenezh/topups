package com.example.topups.domain.services;

import com.example.topups.domain.entities.Login;
import com.example.topups.domain.entities.User;
import com.example.topups.domain.ports.LoginRepository;
import com.example.topups.domain.ports.UserRepository;
import com.example.topups.infrastructure.config.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(User user, Login login) {
        logger.info("Iniciando el registro de usuario con email: {} y username: {}", user.getEmail(), login.getUsername());

        Optional<User> userExist = userRepository.findByEmail(user.getEmail());
        Optional<Login> loginExist = loginRepository.findByUsername(login.getUsername());

        if (userExist.isPresent() || loginExist.isPresent()) {
            logger.warn("El registro falló: ya existe un usuario con email: {} o un login con username: {}", user.getEmail(), login.getUsername());
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "El usuario ya existe");
        }

        User userSave = userRepository.save(user);
        logger.info("Usuario registrado exitosamente: ID={}, email={}", userSave.getId(), userSave.getEmail());

        login.setPassword(passwordEncoder.encode(login.getPassword()));
        login.setUser(userSave);

        Login userLogin = loginRepository.save(login);
        logger.info("Login registrado exitosamente para el usuario: username={}, userID={}", userLogin.getUsername(), userLogin.getUser().getId());
    }
}
