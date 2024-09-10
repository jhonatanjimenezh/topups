package com.example.topups.infrastructure.controllers.dto.request;

import com.example.topups.domain.entities.Operator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class OperatorRequestDTO {

    private static final Logger logger = LoggerFactory.getLogger(OperatorRequestDTO.class);

    private Long id;

    @NotNull(message = "El nombre del operador es obligatorio.")
    @NotBlank(message = "El campo nombre no puede estar vacío.")
    private String name;

    public Operator toModel() {
        logger.info("Iniciando conversión de OperatorRequestDTO a modelo de entidad Operator. Datos recibidos: id={}, nombre={}", id, name);

        Operator operator = new Operator();
        operator.setId(this.getId());
        operator.setName(this.getName());

        logger.info("Conversión de OperatorRequestDTO completada. Datos del modelo Operator: id={}, nombre={}", operator.getId(), operator.getName());

        return operator;
    }
}
