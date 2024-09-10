package com.example.topups.application.usecases;

import com.example.topups.application.ports.OperatorUseCase;
import com.example.topups.domain.entities.Operator;
import com.example.topups.domain.services.OperatorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorUseCaseImpl implements OperatorUseCase {

    private static final Logger logger = LoggerFactory.getLogger(OperatorUseCaseImpl.class);
    private final OperatorService operatorService;

    @Override
    public void registerOperator(Operator operator) {
        try {
            logger.info("Iniciando el registro de un nuevo operador con los datos: {}", operator);
            operatorService.addOperator(operator);
            logger.info("Operador registrado exitosamente: {}", operator);
        } catch (Exception e) {
            logger.error("Error durante el registro del operador: {}", operator, e);
            throw e;
        }
    }

    @Override
    public List<Operator> getAllOperators() {
        try {
            logger.info("Iniciando la consulta de todos los operadores");
            List<Operator> operators = operatorService.getAllOperators();
            logger.info("Lista de operadores obtenida exitosamente. Total operadores: {}", operators.size());
            return operators;
        } catch (Exception e) {
            logger.error("Error al obtener la lista de operadores", e);
            throw e;
        }
    }
}
