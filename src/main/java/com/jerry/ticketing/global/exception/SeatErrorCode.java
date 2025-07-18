package com.jerry.ticketing.global.exception;

import com.jerry.ticketing.global.exception.common.ErrorCode;

public enum SeatErrorCode implements ErrorCode {
    SEAT_NOT_FOUND("요청 좌석 중 일부를 찾을 수 없습니다."),
    SEAT_ALREADY_BLOCKED("이미 선점된 좌석이 포함되어 있습니다.");

    private final String message;

    SeatErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
