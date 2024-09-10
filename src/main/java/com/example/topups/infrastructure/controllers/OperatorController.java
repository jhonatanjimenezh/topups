package com.example.topups.infrastructure.controllers;

import com.example.topups.application.ports.OperatorUseCase;
import com.example.topups.domain.entities.Operator;
import com.example.topups.infrastructure.controllers.dto.ResponseDTO;
import com.example.topups.infrastructure.controllers.dto.request.OperatorRequestDTO;
import com.example.topups.infrastructure.controllers.dto.response.OperatorResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/operators")
@RequiredArgsConstructor
public class OperatorController {

    private static final Logger logger = LoggerFactory.getLogger(OperatorController.class);
    private final OperatorUseCase operatorUseCase;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<OperatorResponseDTO>>> getAllOperators() {
        logger.info("Iniciando proceso para obtener todos los operadores registrados.");
        try {
            List<Operator> operators = operatorUseCase.getAllOperators();
            List<OperatorResponseDTO> response = operators.stream()
                    .map(OperatorResponseDTO::fromModel)
                    .collect(Collectors.toList());
            logger.info("Operadores obtenidos exitosamente. Total de operadores: {}", response.size());
            return ResponseEntity.ok(new ResponseDTO<>(
                    true, HttpStatus.OK.value(), "Lista de operadores obtenida exitosamente", response
            ));
        } catch (Exception ex) {
            logger.error("Error al obtener la lista de operadores. Detalle: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(
                    false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error al obtener la lista de operadores", null
            ));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<Void>> registerOperator(@Valid @RequestBody OperatorRequestDTO operatorRequestDTO) {
        logger.info("Iniciando proceso para registrar un nuevo operador con los siguientes datos: {}", operatorRequestDTO);
        try {
            Operator operator = operatorRequestDTO.toModel();
            operatorUseCase.registerOperator(operator);
            logger.info("Operador registrado exitosamente: {}", operator.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(
                    true, HttpStatus.CREATED.value(), "Operador registrado exitosamente", null
            ));
        } catch (Exception ex) {
            logger.error("Error al registrar el operador: {}. Detalle: {}", operatorRequestDTO.getName(), ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(
                    false, HttpStatus.BAD_REQUEST.value(), "Ocurrió un error al registrar el operador", null
            ));
        }
    }
}
