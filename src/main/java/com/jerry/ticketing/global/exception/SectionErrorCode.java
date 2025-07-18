package com.jerry.ticketing.global.exception;


import com.jerry.ticketing.global.exception.common.ErrorCode;

public enum SectionErrorCode implements ErrorCode {
    SECTION_NOT_FOUND("저장된 구역이 없습니다."),
    SECTION_SOLD_OUT("해당 섹션의 남은 좌석이 없습니다.");


    private final String message;

    SectionErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
