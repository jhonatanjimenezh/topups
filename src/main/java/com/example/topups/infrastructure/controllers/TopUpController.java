package com.example.topups.infrastructure.controllers;

import com.example.topups.application.ports.TopUpUseCase;
import com.example.topups.domain.entities.TopUp;
import com.example.topups.infrastructure.config.exception.CustomException;
import com.example.topups.infrastructure.config.security.CustomUserDetails;
import com.example.topups.infrastructure.config.security.JwtUtil;
import com.example.topups.infrastructure.controllers.dto.ResponseDTO;
import com.example.topups.infrastructure.controllers.dto.request.TopUpRequestDTO;
import com.example.topups.infrastructure.controllers.dto.response.TopUpResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topups")
@RequiredArgsConstructor
public class TopUpController {

    private static final Logger logger = LoggerFactory.getLogger(TopUpController.class);
    private final TopUpUseCase topUpUseCase;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Void>> performTopUp(@Valid @RequestBody TopUpRequestDTO topUpRequestDTO, @RequestHeader("Authorization") String token) {
        try {

            String jwt = token.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            Long userId = ((CustomUserDetails) userDetailsService.loadUserByUsername(username)).getUserId();

            logger.info("Iniciando el proceso de recarga para el usuario con ID: {} y los siguientes datos: {}", userId, topUpRequestDTO);

            TopUp topUp = topUpRequestDTO.toModel();
            topUp.setUserId(userId);

            topUpUseCase.performTopUp(topUp);

            logger.info("Recarga realizada exitosamente para el usuario con ID: {}. Monto={}, número de teléfono={}, operadorId={}",
                    userId, topUp.getAmount(), topUp.getNumberPhone(), topUp.getOperatorId());

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(
                    true, HttpStatus.OK.value(), "Recarga realizada exitosamente", null
            ));
        } catch (CustomException ex) {
            logger.error("Error al realizar la recarga. Detalle: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(
                    false, HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null
            ));
        }catch (Exception ex) {
            logger.error("Error al realizar la recarga. Detalle: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(
                    false, HttpStatus.BAD_REQUEST.value(), "Ocurrió un error al realizar la recarga", null
            ));
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO<List<TopUpResponseDTO>>> getTopUpsByUser(@PathVariable Long userId) {
        logger.info("Iniciando consulta de recargas para el usuario con ID: {}", userId);
        try {
            List<TopUp> topUps = topUpUseCase.getTopUpsByUser(userId);
            List<TopUpResponseDTO> response = topUps.stream().map(TopUpResponseDTO::fromModel).collect(Collectors.toList());
            logger.info("Consulta de recargas por usuario exitosa. Total de recargas: {}", response.size());
            return ResponseEntity.ok(new ResponseDTO<>(
                    true, HttpStatus.OK.value(), "Recargas obtenidas exitosamente", response
            ));
        } catch (Exception ex) {
            logger.error("Error al consultar las recargas del usuario con ID: {}. Detalle: {}", userId, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(
                    false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error al obtener las recargas del usuario", null
            ));
        }
    }

    @GetMapping("/operator/{operatorId}")
    public ResponseEntity<ResponseDTO<List<TopUpResponseDTO>>> getTopUpsByOperator(@PathVariable Long operatorId) {
        logger.info("Iniciando consulta de recargas para el operador con ID: {}", operatorId);
        try {
            List<TopUp> topUps = topUpUseCase.getTopUpsByOperator(operatorId);
            List<TopUpResponseDTO> response = topUps.stream().map(TopUpResponseDTO::fromModel).collect(Collectors.toList());
            logger.info("Consulta de recargas por operador exitosa. Total de recargas: {}", response.size());
            return ResponseEntity.ok(new ResponseDTO<>(
                    true, HttpStatus.OK.value(), "Recargas obtenidas exitosamente", response
            ));
        } catch (Exception ex) {
            logger.error("Error al consultar las recargas del operador con ID: {}. Detalle: {}", operatorId, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(
                    false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error al obtener las recargas del operador", null
            ));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<TopUpResponseDTO>>> getAllTopUps() {
        logger.info("Iniciando consulta de todas las recargas registradas.");
        try {
            List<TopUp> topUps = topUpUseCase.getAllTopUps();
            List<TopUpResponseDTO> response = topUps.stream().map(TopUpResponseDTO::fromModel).collect(Collectors.toList());
            logger.info("Consulta de todas las recargas exitosa. Total de recargas: {}", response.size());
            return ResponseEntity.ok(new ResponseDTO<>(
                    true, HttpStatus.OK.value(), "Todas las recargas obtenidas exitosamente", response
            ));
        } catch (Exception ex) {
            logger.error("Error al consultar todas las recargas. Detalle: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(
                    false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error al obtener todas las recargas", null
            ));
        }
    }
}
