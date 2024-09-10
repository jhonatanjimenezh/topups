package com.example.topups.domain.services;

import com.example.topups.domain.entities.Login;
import com.example.topups.domain.entities.User;
import com.example.topups.domain.ports.LoginRepository;
import com.example.topups.domain.ports.UserRepository;
import com.example.topups.infrastructure.config.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private Login testLogin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setName("Test User");
        testUser.setPhone("55555555");

        testLogin = new Login();
        testLogin.setUsername("testuser");
        testLogin.setPassword("password123");
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());
        when(loginRepository.findByUsername(testLogin.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(loginRepository.save(any(Login.class))).thenReturn(testLogin);
        when(passwordEncoder.encode(testLogin.getPassword())).thenReturn("encodedPassword");

        userService.registerUser(testUser, testLogin);

        verify(userRepository).save(testUser);
        verify(loginRepository).save(testLogin);

        assertEquals("encodedPassword", testLogin.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenUserOrLoginAlreadyExists() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.registerUser(testUser, testLogin);
        });

        assertEquals("El usuario ya existe", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
        verify(loginRepository, never()).save(any(Login.class));
    }

    @Test
    void shouldThrowExceptionWhenLoginAlreadyExists() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());
        when(loginRepository.findByUsername(testLogin.getUsername())).thenReturn(Optional.of(testLogin));

        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.registerUser(testUser, testLogin);
        });

        assertEquals("El usuario ya existe", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
        verify(loginRepository, never()).save(any(Login.class));
    }
}
