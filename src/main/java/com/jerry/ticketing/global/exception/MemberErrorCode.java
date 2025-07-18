package com.jerry.ticketing.global.exception;


import com.jerry.ticketing.global.exception.common.ErrorCode;

public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND("저장된 멤버가 없습니다.");

    private final String message;

    MemberErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
