package com.example.topups.application.usecases;

import com.example.topups.domain.entities.TopUp;
import com.example.topups.domain.services.TopUpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PerformTopUpUseCaseTest {

    @Mock
    private TopUpService topUpService;

    @InjectMocks
    private PerformTopUpUseCase performTopUpUseCase;

    private TopUp testTopUp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testTopUp = new TopUp();
        testTopUp.setId(1L);
        testTopUp.setAmount(100.0);
        testTopUp.setNumberPhone("1234567890");
        testTopUp.setOperatorId(1L);
    }

    @Test
    void shouldPerformTopUpSuccessfully() {
        performTopUpUseCase.performTopUp(testTopUp);

        verify(topUpService, times(1)).performTopUp(testTopUp);

        assertDoesNotThrow(() -> performTopUpUseCase.performTopUp(testTopUp));
    }

    @Test
    void shouldThrowExceptionWhenPerformTopUpFails() {
        doThrow(new RuntimeException("Error al realizar la recarga")).when(topUpService).performTopUp(testTopUp);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> performTopUpUseCase.performTopUp(testTopUp));
        assertEquals("Error al realizar la recarga", exception.getMessage());

        verify(topUpService, times(1)).performTopUp(testTopUp);
    }

    @Test
    void shouldGetTopUpsByUserSuccessfully() {
        List<TopUp> mockTopUps = Arrays.asList(
                new TopUp(1L, 100.0, null, "1234567890", 1L, 1L),
                new TopUp(2L, 200.0, null, "0987654321", 2L, 1L)
        );

        when(topUpService.getTopUpsByUser(1L)).thenReturn(mockTopUps);

        List<TopUp> topUps = performTopUpUseCase.getTopUpsByUser(1L);

        assertEquals(2, topUps.size());

        verify(topUpService, times(1)).getTopUpsByUser(1L);
    }

    @Test
    void shouldThrowExceptionWhenGetTopUpsByUserFails() {
        doThrow(new RuntimeException("Error al obtener las recargas por usuario")).when(topUpService).getTopUpsByUser(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> performTopUpUseCase.getTopUpsByUser(1L));

        assertEquals("Error al obtener las recargas por usuario", exception.getMessage());

        verify(topUpService, times(1)).getTopUpsByUser(1L);
    }

    @Test
    void shouldGetTopUpsByOperatorSuccessfully() {
        List<TopUp> mockTopUps = Arrays.asList(
                new TopUp(1L, 100.0, null, "1234567890", 1L, 1L),
                new TopUp(2L, 200.0, null, "0987654321", 1L, 2L)
        );

        when(topUpService.getTopUpsByOperator(1L)).thenReturn(mockTopUps);

        List<TopUp> topUps = performTopUpUseCase.getTopUpsByOperator(1L);

        assertEquals(2, topUps.size());

        verify(topUpService, times(1)).getTopUpsByOperator(1L);
    }

    @Test
    void shouldThrowExceptionWhenGetTopUpsByOperatorFails() {
        doThrow(new RuntimeException("Error al obtener las recargas por operador")).when(topUpService).getTopUpsByOperator(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> performTopUpUseCase.getTopUpsByOperator(1L));

        assertEquals("Error al obtener las recargas por operador", exception.getMessage());

        verify(topUpService, times(1)).getTopUpsByOperator(1L);
    }

    @Test
    void shouldGetAllTopUpsSuccessfully() {
        List<TopUp> mockTopUps = Arrays.asList(
                new TopUp(1L, 100.0, null, "1234567890", 1L, 1L),
                new TopUp(2L, 200.0, null, "0987654321", 1L, 2L)
        );

        when(topUpService.getAllTopUps()).thenReturn(mockTopUps);
        List<TopUp> topUps = performTopUpUseCase.getAllTopUps();
        assertEquals(2, topUps.size());
        verify(topUpService, times(1)).getAllTopUps();
    }

    @Test
    void shouldThrowExceptionWhenGetAllTopUpsFails() {
        doThrow(new RuntimeException("Error al obtener todas las recargas")).when(topUpService).getAllTopUps();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> performTopUpUseCase.getAllTopUps());
        assertEquals("Error al obtener todas las recargas", exception.getMessage());
        verify(topUpService, times(1)).getAllTopUps();
    }
}
