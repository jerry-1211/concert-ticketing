package com.jerry.ticketing.global.exception;


public enum ReservationErrorCode implements ErrorCode {
    RESERVATION_NOT_FOUND("해당 예약을 찾을 수 없습니다.");

    private final String message;

    ReservationErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
