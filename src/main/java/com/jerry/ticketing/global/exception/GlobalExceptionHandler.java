package com.jerry.ticketing.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        BusinessErrorResponse businessErrorResponse = new BusinessErrorResponse("BUSINESS_ERROR", errorCode.getMessage());
        return new ResponseEntity<>(businessErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
