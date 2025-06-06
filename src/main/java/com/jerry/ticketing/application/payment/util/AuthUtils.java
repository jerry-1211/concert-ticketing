package com.jerry.ticketing.application.payment.util;

import com.jerry.ticketing.global.config.payment.TossPaymentConfig;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthUtils {
    public static String encodeBasicAuth(TossPaymentConfig tossPaymentConfig){
        return Base64.getEncoder()
                .encodeToString((tossPaymentConfig.getTestSecreteApiKey() + ":").getBytes(StandardCharsets.UTF_8));
    }
}
