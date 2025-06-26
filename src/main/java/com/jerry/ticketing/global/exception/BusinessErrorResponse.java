package com.jerry.ticketing.global.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BusinessErrorResponse {
    private String code;
    private String message;
}
