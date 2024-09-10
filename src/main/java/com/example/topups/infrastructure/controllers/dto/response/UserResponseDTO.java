package com.example.topups.infrastructure.controllers.dto.response;

import com.example.topups.domain.entities.User;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class UserResponseDTO {

    private static final Logger logger = LoggerFactory.getLogger(UserResponseDTO.class);

    private String name;
    private String email;
    private String phone;

    public static UserResponseDTO fromModel(User user) {
        logger.info("Iniciando la conversión de la entidad User a UserResponseDTO para el usuario con : {}", user);

        UserResponseDTO dto = new UserResponseDTO();

        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());

        logger.info("Conversión exitosa del usuario con Nombre: {}, Email: {}, Teléfono: {}",
                dto.getName(), dto.getEmail(), dto.getPhone());

        return dto;
    }
}
