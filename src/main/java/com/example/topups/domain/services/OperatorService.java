package com.example.topups.domain.services;

import com.example.topups.domain.entities.Operator;
import com.example.topups.domain.ports.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private static final Logger logger = LoggerFactory.getLogger(OperatorService.class);
    private final OperatorRepository operatorRepository;

    public void addOperator(Operator operator) {
        logger.info("Iniciando la adición de un nuevo operador con los siguientes datos: {}", operator);
        try {
            operatorRepository.save(operator);
            logger.info("Operador agregado exitosamente: ID={}, Nombre={}", operator.getId(), operator.getName());
        } catch (Exception e) {
            logger.error("Error al agregar el operador: {}", operator, e);
            throw e;
        }
    }

    public List<Operator> getAllOperators() {
        logger.info("Iniciando la consulta de todos los operadores registrados.");
        try {
            List<Operator> operators = operatorRepository.findAll();
            logger.info("Consulta exitosa. Total de operadores encontrados: {}", operators.size());
            return operators;
        } catch (Exception e) {
            logger.error("Error al obtener la lista de operadores.", e);
            throw e;
        }
    }
}
