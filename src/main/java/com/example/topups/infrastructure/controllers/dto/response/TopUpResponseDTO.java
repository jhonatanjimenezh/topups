package com.example.topups.infrastructure.controllers.dto.response;

import com.example.topups.domain.entities.TopUp;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Data
public class TopUpResponseDTO {

    private static final Logger logger = LoggerFactory.getLogger(TopUpResponseDTO.class);

    private Double amount;
    private LocalDateTime date;
    private String numberPhone;
    private Long userId;
    private Long operatorId;

    public static TopUpResponseDTO fromModel(TopUp topUp) {
        logger.info("Iniciando conversión de la entidad TopUp a TopUpResponseDTO para la recarga con ID: {}", topUp.getId());

        TopUpResponseDTO dto = new TopUpResponseDTO();
        dto.setAmount(topUp.getAmount());
        dto.setDate(topUp.getDate());
        dto.setNumberPhone(topUp.getNumberPhone());
        dto.setUserId(topUp.getUserId());
        dto.setOperatorId(topUp.getOperatorId());

        logger.info("Conversión exitosa: Monto={}, Número de Teléfono={}, UserId={}, OperatorId={}",
                dto.getAmount(), dto.getNumberPhone(), dto.getUserId(), dto.getOperatorId());

        return dto;
    }
}
