package com.jerry.ticketing.global.exception;


public enum SectionErrorCode implements ErrorCode {
    SECTION_NOT_FOUND("저장된 콘서트가 없습니다.");

    private final String message;

    SectionErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
