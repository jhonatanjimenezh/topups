package com.example.topups.domain.services;

import com.example.topups.domain.entities.Operator;
import com.example.topups.domain.entities.TopUp;
import com.example.topups.domain.ports.OperatorRepository;
import com.example.topups.domain.ports.TopUpRepository;
import com.example.topups.infrastructure.config.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopUpService {

    private static final Logger logger = LoggerFactory.getLogger(TopUpService.class);
    private final TopUpRepository topUpRepository;
    private final OperatorRepository operatorRepository;

    public void performTopUp(TopUp topUp) {
        logger.info("Iniciando el proceso de recarga para el número de teléfono: {}, monto: {}, operadorId: {}",
                topUp.getNumberPhone(), topUp.getAmount(), topUp.getOperatorId());

        Optional<Operator> operatorExist = operatorRepository.findById(topUp.getOperatorId());

        if (!operatorExist.isPresent()) {
            logger.error("Error: El operador con ID {} no existe. No se puede realizar la recarga para el número: {}",
                    topUp.getOperatorId(), topUp.getNumberPhone());
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "El operador no existe");
        }

        topUpRepository.save(topUp);
        logger.info("Recarga realizada exitosamente para el número de teléfono: {}, monto: {}, operadorId: {}",
                topUp.getNumberPhone(), topUp.getAmount(), topUp.getOperatorId());
    }

    public List<TopUp> getTopUpsByUser(Long userId) {
        logger.info("Obteniendo todas las recargas realizadas por el usuario con ID: {}", userId);
        List<TopUp> topUps = topUpRepository.findByUserId(userId);
        logger.info("Se obtuvieron {} recargas para el usuario con ID: {}", topUps.size(), userId);
        return topUps;
    }

    public List<TopUp> getTopUpsByOperator(Long operatorId) {
        logger.info("Obteniendo todas las recargas realizadas para el operador con ID: {}", operatorId);
        List<TopUp> topUps = topUpRepository.findByOperatorId(operatorId);
        logger.info("Se obtuvieron {} recargas para el operador con ID: {}", topUps.size(), operatorId);
        return topUps;
    }

    public List<TopUp> getAllTopUps() {
        logger.info("Obteniendo todas las recargas realizadas en el sistema.");
        List<TopUp> topUps = topUpRepository.findAll();
        logger.info("Se obtuvieron {} recargas en total.", topUps.size());
        return topUps;
    }
}
