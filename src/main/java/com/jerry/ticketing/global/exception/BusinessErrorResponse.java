package com.jerry.ticketing.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusinessErrorResponse {
    private String code;
    private String message;
}
