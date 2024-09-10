package com.example.topups.infrastructure.controllers;

import com.example.topups.application.ports.OperatorUseCase;
import com.example.topups.domain.entities.Operator;
import com.example.topups.infrastructure.controllers.dto.ResponseDTO;
import com.example.topups.infrastructure.controllers.dto.request.OperatorRequestDTO;
import com.example.topups.infrastructure.controllers.dto.response.OperatorResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OperatorControllerTest {

    @Mock
    private OperatorUseCase operatorUseCase;

    @InjectMocks
    private OperatorController operatorController;

    private Operator testOperator;
    private OperatorRequestDTO operatorRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testOperator = new Operator();
        testOperator.setId(1L);
        testOperator.setName("Operator Test");

        operatorRequestDTO = new OperatorRequestDTO();
        operatorRequestDTO.setId(1L);
        operatorRequestDTO.setName("Operator Test");
    }

    @Test
    void shouldGetAllOperatorsSuccessfully() {
        List<Operator> mockOperators = Arrays.asList(
                new Operator(1L, "Operator A"),
                new Operator(2L, "Operator B")
        );

        when(operatorUseCase.getAllOperators()).thenReturn(mockOperators);

        ResponseEntity<ResponseDTO<List<OperatorResponseDTO>>> response = operatorController.getAllOperators();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isStatus());
        assertEquals(2, response.getBody().getData().size());

        verify(operatorUseCase, times(1)).getAllOperators();
    }

    @Test
    void shouldHandleErrorWhenGettingAllOperators() {
        when(operatorUseCase.getAllOperators()).thenThrow(new RuntimeException("Error al obtener operadores"));

        ResponseEntity<ResponseDTO<List<OperatorResponseDTO>>> response = operatorController.getAllOperators();

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody().isStatus());
        assertNull(response.getBody().getData());

        verify(operatorUseCase, times(1)).getAllOperators();
    }

    @Test
    void shouldRegisterOperatorSuccessfully() {
        doNothing().when(operatorUseCase).registerOperator(any(Operator.class));

        ResponseEntity<ResponseDTO<Void>> response = operatorController.registerOperator(operatorRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().isStatus());

        verify(operatorUseCase, times(1)).registerOperator(any(Operator.class));
    }

    @Test
    void shouldHandleErrorWhenRegisteringOperator() {
        doThrow(new RuntimeException("Error al registrar el operador")).when(operatorUseCase).registerOperator(any(Operator.class));

        ResponseEntity<ResponseDTO<Void>> response = operatorController.registerOperator(operatorRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isStatus());

        verify(operatorUseCase, times(1)).registerOperator(any(Operator.class));
    }
}
