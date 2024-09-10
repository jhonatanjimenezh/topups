package com.example.topups.infrastructure.controllers;

import com.example.topups.infrastructure.controllers.dto.ResponseDTO;
import com.example.topups.infrastructure.controllers.dto.request.AuthRequestDTO;
import com.example.topups.infrastructure.config.security.JwtUtil;
import com.example.topups.infrastructure.controllers.dto.response.AuthResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO<AuthResponseDTO>> createAuthenticationToken(@Valid @RequestBody AuthRequestDTO authRequest) {
        logger.info("Iniciando autenticación para el usuario: {}", authRequest.getUsername());
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            logger.debug("Credenciales validadas para el usuario: {}", authRequest.getUsername());

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            logger.info("Usuario encontrado en el sistema: {}", userDetails.getUsername());

            AuthResponseDTO response = jwtUtil.generateToken(userDetails.getUsername());
            logger.info("Token JWT generado exitosamente para el usuario: {}", userDetails.getUsername());

            return ResponseEntity.ok(new ResponseDTO<>(
                    true, HttpStatus.OK.value(), "Usuario autenticado exitosamente", response
            ));
        } catch (BadCredentialsException ex) {
            logger.warn("Fallo de autenticación para el usuario: {}. Credenciales incorrectas.", authRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO<>(
                    false, HttpStatus.UNAUTHORIZED.value(), "Credenciales incorrectas", null
            ));
        } catch (Exception ex) {
            logger.error("Error interno durante la autenticación para el usuario: {}. Detalle: {}", authRequest.getUsername(), ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(
                    false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno durante la autenticación", null
            ));
        }
    }
}
