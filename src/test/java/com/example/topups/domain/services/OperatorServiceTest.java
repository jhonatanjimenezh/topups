package com.example.topups.domain.services;

import com.example.topups.domain.entities.Operator;
import com.example.topups.domain.ports.OperatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OperatorServiceTest {

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private OperatorService operatorService;

    private Operator testOperator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testOperator = new Operator();
        testOperator.setId(1L);
        testOperator.setName("Test Operator");
    }

    @Test
    void shouldAddOperatorSuccessfully() {
        when(operatorRepository.save(any(Operator.class))).thenReturn(testOperator);

        operatorService.addOperator(testOperator);

        verify(operatorRepository, times(1)).save(testOperator);
        assertDoesNotThrow(() -> operatorService.addOperator(testOperator));
    }

    @Test
    void shouldThrowExceptionWhenAddOperatorFails() {
        doThrow(new RuntimeException("Database error")).when(operatorRepository).save(testOperator);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            operatorService.addOperator(testOperator);
        });

        assertEquals("Database error", exception.getMessage());
        verify(operatorRepository, times(1)).save(testOperator);
    }

    @Test
    void shouldGetAllOperatorsSuccessfully() {
        List<Operator> operators = Arrays.asList(testOperator, new Operator(2L, "Operator 2"));
        when(operatorRepository.findAll()).thenReturn(operators);

        List<Operator> result = operatorService.getAllOperators();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(operatorRepository, times(1)).findAll();
    }

    @Test
    void shouldThrowExceptionWhenGetAllOperatorsFails() {
        when(operatorRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            operatorService.getAllOperators();
        });

        assertEquals("Database error", exception.getMessage());
        verify(operatorRepository, times(1)).findAll();
    }
}
