package com.example.topups.infrastructure.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {
    private boolean status;
    private int code;
    private String message;
    private T data;
}
