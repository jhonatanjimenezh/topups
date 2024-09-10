package com.example.topups.infrastructure.controllers.dto.request;

import com.example.topups.domain.entities.TopUp;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class TopUpRequestDTO {

    private static final Logger logger = LoggerFactory.getLogger(TopUpRequestDTO.class);

    @NotNull(message = "El monto es obligatorio.")
    @Min(value = 1000, message = "El monto debe ser mayor que 1000")
    @Max(value = 1000000, message = "El monto debe ser menor que 1000000")
    private Double amount;

    @NotNull(message = "El número de teléfono es obligatorio.")
    @NotBlank(message = "El campo número de teléfono no puede estar vacío.")
    @Size(min = 10, message = "El teléfono debe tener al menos 10 caracteres.")
    @Size(max = 12, message = "El teléfono no debe exceder los 12 caracteres.")
    private String numberPhone;

    @NotNull(message = "El operadorId es obligatorio.")
    private Long operatorId;

    public TopUp toModel() {
        logger.info("Iniciando la conversión de TopUpRequestDTO a modelo TopUp. Datos: monto={}, número de teléfono={}, operadorId={}",
                amount, numberPhone, operatorId);

        TopUp topUp = new TopUp();
        topUp.setAmount(this.amount);
        topUp.setNumberPhone(this.numberPhone);
        topUp.setOperatorId(this.operatorId);

        logger.info("Conversión de TopUpRequestDTO completada. Modelo TopUp: {}", topUp);
        return topUp;
    }
}
