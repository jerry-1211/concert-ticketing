package com.jerry.ticketing.global.exception.common;


import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());  // RuntimeException 부모에게 콘솔에 출력되는 에러 메시지 전달
        this.errorCode = errorCode;
    }

}
