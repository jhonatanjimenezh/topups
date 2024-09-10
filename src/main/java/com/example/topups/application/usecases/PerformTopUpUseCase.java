package com.example.topups.application.usecases;

import com.example.topups.application.ports.TopUpUseCase;
import com.example.topups.domain.entities.TopUp;
import com.example.topups.domain.services.TopUpService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerformTopUpUseCase implements TopUpUseCase {

    private static final Logger logger = LoggerFactory.getLogger(PerformTopUpUseCase.class);
    private final TopUpService topUpService;

    @Override
    public void performTopUp(TopUp topUp) {
        logger.info("Iniciando proceso de recarga para el número de teléfono: {}, monto: {}, operadorId: {}",
                topUp.getNumberPhone(), topUp.getAmount(), topUp.getOperatorId());
        try {
            topUpService.performTopUp(topUp);
            logger.info("Recarga realizada exitosamente para el número de teléfono: {}, monto: {}, operadorId: {}",
                    topUp.getNumberPhone(), topUp.getAmount(), topUp.getOperatorId());
        } catch (Exception ex) {
            logger.error("Error al realizar la recarga para el número de teléfono: {}, monto: {}, operadorId: {}. Detalle del error: {}",
                    topUp.getNumberPhone(), topUp.getAmount(), topUp.getOperatorId(), ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public List<TopUp> getTopUpsByUser(Long userId) {
        logger.info("Obteniendo todas las recargas para el usuario con ID: {}", userId);
        List<TopUp> topUps = topUpService.getTopUpsByUser(userId);
        logger.info("Se obtuvieron {} recargas para el usuario con ID: {}", topUps.size(), userId);
        return topUps;
    }

    @Override
    public List<TopUp> getTopUpsByOperator(Long operatorId) {
        logger.info("Obteniendo todas las recargas para el operador con ID: {}", operatorId);
        List<TopUp> topUps = topUpService.getTopUpsByOperator(operatorId);
        logger.info("Se obtuvieron {} recargas para el operador con ID: {}", topUps.size(), operatorId);
        return topUps;
    }

    @Override
    public List<TopUp> getAllTopUps() {
        logger.info("Obteniendo todas las recargas realizadas en el sistema.");
        List<TopUp> topUps = topUpService.getAllTopUps();
        logger.info("Se obtuvieron {} recargas en total.", topUps.size());
        return topUps;
    }
}
