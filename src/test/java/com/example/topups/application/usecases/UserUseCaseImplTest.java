package com.example.topups.application.usecases;

import com.example.topups.domain.entities.Login;
import com.example.topups.domain.entities.User;
import com.example.topups.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseImplTest {

    @Mock
    private UserService userService;

    @Mock
    private Logger logger;

    @InjectMocks
    private UserUseCaseImpl userUseCaseImpl;

    private User testUser;
    private Login testLogin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("testuser@example.com");

        testLogin = new Login();
        testLogin.setUsername("testuser");
        testLogin.setPassword("password123");
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        userUseCaseImpl.registerUser(testUser, testLogin);

        verify(userService, times(1)).registerUser(testUser, testLogin);
        assertDoesNotThrow(() -> userUseCaseImpl.registerUser(testUser, testLogin));
    }

    @Test
    void shouldThrowExceptionWhenRegisterUserFails() {
        doThrow(new RuntimeException("Error de prueba")).when(userService).registerUser(testUser, testLogin);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userUseCaseImpl.registerUser(testUser, testLogin));
        assertEquals("Error de prueba", exception.getMessage());
        verify(userService, times(1)).registerUser(testUser, testLogin);

    }
}
