package com.jerry.ticketing.global.exception;

public interface ErrorCode {
        // 에러 메시지
        String getMessage();

        // 에러 코드 이름 (Eum에서 자동 제공)
        String name();
}
