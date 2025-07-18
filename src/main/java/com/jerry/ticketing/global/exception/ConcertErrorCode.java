package com.jerry.ticketing.global.exception;


import com.jerry.ticketing.global.exception.common.ErrorCode;

public enum ConcertErrorCode implements ErrorCode {
    CONCERT_SAVE_FAILED("콘서트 저장에 실패하였습니다."),
    CONCERT_NOT_FOUND("저장된 콘서트가 없습니다.");

    private final String message;

    ConcertErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
