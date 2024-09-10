package com.example.topups.infrastructure.config.security;

import com.example.topups.infrastructure.controllers.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ResponseDTO<Void> responseDTO = new ResponseDTO<>(
                false,
                HttpStatus.UNAUTHORIZED.value(),
                "Debe iniciar sesión: " + authException.getMessage(),
                null
        );

        String jsonResponse = new ObjectMapper().writeValueAsString(responseDTO);

        response.getWriter().write(jsonResponse);
    }
}
