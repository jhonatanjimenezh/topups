package com.example.topups.application.usecases;

import com.example.topups.domain.entities.Operator;
import com.example.topups.domain.services.OperatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OperatorUseCaseImplTest {

    @Mock
    private OperatorService operatorService;

    @InjectMocks
    private OperatorUseCaseImpl operatorUseCaseImpl;

    private Operator testOperator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testOperator = new Operator();
        testOperator.setId(1L);
        testOperator.setName("Operator Test");
    }

    @Test
    void shouldRegisterOperatorSuccessfully() {
        operatorUseCaseImpl.registerOperator(testOperator);

        verify(operatorService, times(1)).addOperator(testOperator);

        assertDoesNotThrow(() -> operatorUseCaseImpl.registerOperator(testOperator));
    }

    @Test
    void shouldThrowExceptionWhenRegisterOperatorFails() {
        doThrow(new RuntimeException("Error al registrar operador")).when(operatorService).addOperator(testOperator);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> operatorUseCaseImpl.registerOperator(testOperator));

        assertEquals("Error al registrar operador", exception.getMessage());

        verify(operatorService, times(1)).addOperator(testOperator);
    }

    @Test
    void shouldGetAllOperatorsSuccessfully() {
        List<Operator> mockOperators = Arrays.asList(
                new Operator(1L, "Operator 1"),
                new Operator(2L, "Operator 2")
        );

        when(operatorService.getAllOperators()).thenReturn(mockOperators);

        List<Operator> operators = operatorUseCaseImpl.getAllOperators();

        assertEquals(2, operators.size());

        verify(operatorService, times(1)).getAllOperators();
    }

    @Test
    void shouldThrowExceptionWhenGetAllOperatorsFails() {
        doThrow(new RuntimeException("Error al obtener operadores")).when(operatorService).getAllOperators();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> operatorUseCaseImpl.getAllOperators());

        assertEquals("Error al obtener operadores", exception.getMessage());

        verify(operatorService, times(1)).getAllOperators();
    }
}
