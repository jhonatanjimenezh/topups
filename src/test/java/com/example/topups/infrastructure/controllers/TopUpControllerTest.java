package com.example.topups.infrastructure.controllers;

import com.example.topups.application.ports.TopUpUseCase;
import com.example.topups.domain.entities.TopUp;
import com.example.topups.infrastructure.config.exception.CustomException;
import com.example.topups.infrastructure.config.security.CustomUserDetails;
import com.example.topups.infrastructure.config.security.JwtUtil;
import com.example.topups.infrastructure.controllers.dto.ResponseDTO;
import com.example.topups.infrastructure.controllers.dto.request.TopUpRequestDTO;
import com.example.topups.infrastructure.controllers.dto.response.TopUpResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TopUpControllerTest {

    @Mock
    private TopUpUseCase topUpUseCase;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private TopUpController topUpController;

    private TopUpRequestDTO topUpRequestDTO;
    private TopUp topUp;
    private CustomUserDetails userDetails;
    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        topUpRequestDTO = new TopUpRequestDTO();
        topUpRequestDTO.setAmount(5000.0);
        topUpRequestDTO.setNumberPhone("1234567890");
        topUpRequestDTO.setOperatorId(1L);

        topUp = new TopUp();
        topUp.setId(1L);
        topUp.setAmount(5000.0);
        topUp.setNumberPhone("1234567890");
        topUp.setOperatorId(1L);

        userDetails = mock(CustomUserDetails.class);
        when(userDetails.getUserId()).thenReturn(1L);

        token = "Bearer token-sample";
    }

    @Test
    void shouldPerformTopUpSuccessfully() {
        when(jwtUtil.extractUsername(anyString())).thenReturn("testuser");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);

        doNothing().when(topUpUseCase).performTopUp(any(TopUp.class));

        ResponseEntity<ResponseDTO<Void>> response = topUpController.performTopUp(topUpRequestDTO, token);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isStatus());
        assertEquals("Recarga realizada exitosamente", response.getBody().getMessage());

        verify(topUpUseCase, times(1)).performTopUp(any(TopUp.class));

        verify(jwtUtil, times(1)).extractUsername(anyString());
        verify(userDetailsService, times(1)).loadUserByUsername(anyString());
    }

    @Test
    void shouldHandleCustomExceptionDuringTopUp() {
        when(jwtUtil.extractUsername(anyString())).thenReturn("testuser");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);

        doThrow(new CustomException(HttpStatus.BAD_REQUEST.value(), "El operador no existe")).when(topUpUseCase).performTopUp(any(TopUp.class));

        ResponseEntity<ResponseDTO<Void>> response = topUpController.performTopUp(topUpRequestDTO, token);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isStatus());
        assertEquals("El operador no existe", response.getBody().getMessage());

        verify(topUpUseCase, times(1)).performTopUp(any(TopUp.class));
    }

    @Test
    void shouldReturnTopUpsByUserSuccessfully() {
        when(topUpUseCase.getTopUpsByUser(anyLong())).thenReturn(Collections.singletonList(topUp));

        ResponseEntity<ResponseDTO<List<TopUpResponseDTO>>> response = topUpController.getTopUpsByUser(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isStatus());
        assertEquals(1, response.getBody().getData().size());
        assertEquals("Recargas obtenidas exitosamente", response.getBody().getMessage());

        verify(topUpUseCase, times(1)).getTopUpsByUser(1L);
    }

    @Test
    void shouldReturnTopUpsByOperatorSuccessfully() {
        when(topUpUseCase.getTopUpsByOperator(anyLong())).thenReturn(Collections.singletonList(topUp));

        ResponseEntity<ResponseDTO<List<TopUpResponseDTO>>> response = topUpController.getTopUpsByOperator(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isStatus());
        assertEquals(1, response.getBody().getData().size());
        assertEquals("Recargas obtenidas exitosamente", response.getBody().getMessage());

        verify(topUpUseCase, times(1)).getTopUpsByOperator(1L);
    }

    @Test
    void shouldReturnAllTopUpsSuccessfully() {
        when(topUpUseCase.getAllTopUps()).thenReturn(Collections.singletonList(topUp));

        ResponseEntity<ResponseDTO<List<TopUpResponseDTO>>> response = topUpController.getAllTopUps();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isStatus());
        assertEquals(1, response.getBody().getData().size());
        assertEquals("Todas las recargas obtenidas exitosamente", response.getBody().getMessage());

        verify(topUpUseCase, times(1)).getAllTopUps();
    }
}
