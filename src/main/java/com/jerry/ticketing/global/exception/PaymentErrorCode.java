package com.jerry.ticketing.global.exception;


import com.jerry.ticketing.global.exception.common.ErrorCode;

public enum PaymentErrorCode implements ErrorCode {
    PAYMENT_NOT_FOUND("결제 정보를 찾을 수 없습니다.");

    private final String message;

    PaymentErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
