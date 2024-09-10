package com.example.topups.infrastructure.controllers;

import com.example.topups.application.ports.UserUseCase;
import com.example.topups.domain.entities.Login;
import com.example.topups.domain.entities.User;
import com.example.topups.infrastructure.controllers.dto.ResponseDTO;
import com.example.topups.infrastructure.controllers.dto.request.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserUseCase userUseCase;

    @InjectMocks
    private UserController userController;

    private UserRequestDTO userRequestDTO;
    private User user;
    private Login login;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Test User");
        userRequestDTO.setEmail("testuser@example.com");
        userRequestDTO.setConfirmEmail("testuser@example.com");
        userRequestDTO.setPhone("1234567890");
        userRequestDTO.setUsername("testuser");
        userRequestDTO.setPassword("password123");
        userRequestDTO.setConfirmPassword("password123");

        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setPhone("1234567890");

        login = new Login();
        login.setUsername("testuser");
        login.setPassword("password123");
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        doNothing().when(userUseCase).registerUser(user, login);

        ResponseEntity<ResponseDTO<Void>> response = userController.registerUser(userRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().isStatus());
        assertEquals("Usuario registrado exitosamente", response.getBody().getMessage());

        verify(userUseCase, times(1)).registerUser(any(User.class), any(Login.class));
    }

    @Test
    void shouldReturnBadRequestOnException() {
        doThrow(new RuntimeException("Error al registrar el usuario")).when(userUseCase).registerUser(any(User.class), any(Login.class));

        ResponseEntity<ResponseDTO<Void>> response = userController.registerUser(userRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isStatus());
        assertEquals("Error al registrar el usuario: Error al registrar el usuario", response.getBody().getMessage());

        verify(userUseCase, times(1)).registerUser(any(User.class), any(Login.class));
    }
}
