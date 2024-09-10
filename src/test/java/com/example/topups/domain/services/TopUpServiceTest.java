package com.example.topups.domain.services;

import com.example.topups.domain.entities.Operator;
import com.example.topups.domain.entities.TopUp;
import com.example.topups.domain.ports.OperatorRepository;
import com.example.topups.domain.ports.TopUpRepository;
import com.example.topups.infrastructure.config.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TopUpServiceTest {

    @Mock
    private TopUpRepository topUpRepository;

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private TopUpService topUpService;

    private TopUp testTopUp;
    private Operator testOperator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testOperator = new Operator();
        testOperator.setId(1L);
        testOperator.setName("Test Operator");

        testTopUp = new TopUp();
        testTopUp.setOperatorId(1L);
        testTopUp.setNumberPhone("1234567890");
        testTopUp.setAmount(100.0);
        testTopUp.setUserId(1L);
    }

    @Test
    void shouldPerformTopUpSuccessfully() {
        when(operatorRepository.findById(testTopUp.getOperatorId())).thenReturn(Optional.of(testOperator));

        topUpService.performTopUp(testTopUp);

        verify(operatorRepository, times(1)).findById(testTopUp.getOperatorId());
        verify(topUpRepository, times(1)).save(testTopUp);

        assertDoesNotThrow(() -> topUpService.performTopUp(testTopUp));
    }

    @Test
    void shouldThrowExceptionWhenOperatorNotFound() {
        when(operatorRepository.findById(testTopUp.getOperatorId())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> topUpService.performTopUp(testTopUp));

        assertEquals("El operador no existe", exception.getMessage());

        verify(operatorRepository, times(1)).findById(testTopUp.getOperatorId());
        verify(topUpRepository, times(0)).save(testTopUp);
    }

    @Test
    void shouldGetTopUpsByUserSuccessfully() {
        List<TopUp> topUps = Arrays.asList(testTopUp, new TopUp());
        when(topUpRepository.findByUserId(1L)).thenReturn(topUps);

        List<TopUp> result = topUpService.getTopUpsByUser(1L);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(topUpRepository, times(1)).findByUserId(1L);
    }

    @Test
    void shouldGetTopUpsByOperatorSuccessfully() {
        List<TopUp> topUps = Arrays.asList(testTopUp, new TopUp());
        when(topUpRepository.findByOperatorId(1L)).thenReturn(topUps);

        List<TopUp> result = topUpService.getTopUpsByOperator(1L);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(topUpRepository, times(1)).findByOperatorId(1L);
    }

    @Test
    void shouldGetAllTopUpsSuccessfully() {
        List<TopUp> topUps = Arrays.asList(testTopUp, new TopUp());
        when(topUpRepository.findAll()).thenReturn(topUps);

        List<TopUp> result = topUpService.getAllTopUps();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(topUpRepository, times(1)).findAll();
    }
}
