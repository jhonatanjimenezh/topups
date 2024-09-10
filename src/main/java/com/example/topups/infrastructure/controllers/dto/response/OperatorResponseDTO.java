package com.example.topups.infrastructure.controllers.dto.response;

import com.example.topups.domain.entities.Operator;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class OperatorResponseDTO {

    private static final Logger logger = LoggerFactory.getLogger(OperatorResponseDTO.class);

    private Long id;
    private String name;

    public static OperatorResponseDTO fromModel(Operator operator) {
        logger.info("Iniciando la conversión de la entidad Operator a OperatorResponseDTO para el operador con ID: {}", operator.getId());

        OperatorResponseDTO dto = new OperatorResponseDTO();
        dto.setId(operator.getId());
        dto.setName(operator.getName());

        logger.info("Conversión exitosa de Operator a OperatorResponseDTO. Datos del DTO: ID={}, Nombre={}",
                dto.getId(), dto.getName());

        return dto;
    }
}
