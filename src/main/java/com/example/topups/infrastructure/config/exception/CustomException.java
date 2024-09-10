package com.example.topups.infrastructure.config.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomException  extends GenericException {
    private static final Logger logger = LoggerFactory.getLogger(CustomException.class);

    public CustomException(int errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
        logger.error("Error CustomException: code:{}, message:{}, cause:{}", errorCode, message, cause.getCause());
    }

    public CustomException(int errorCode, String message) {
        super(errorCode, message);
        logger.error("Error CustomException: code:{}, message:{}", errorCode, message);
    }
}
