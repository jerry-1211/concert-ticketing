package com.jerry.ticketing.global.exception.common;

import com.jerry.ticketing.global.exception.SeatErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        BusinessErrorResponse businessErrorResponse = new BusinessErrorResponse(errorCode.name(), errorCode.getMessage());

        if ((errorCode == SeatErrorCode.SEAT_NOT_FOUND) || (errorCode == SeatErrorCode.SEAT_ALREADY_BLOCKED)) {
            return new ResponseEntity<>(businessErrorResponse, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(businessErrorResponse, HttpStatus.BAD_REQUEST);

    }


}
