package com.jerry.ticketing.payment.util;

import com.jerry.ticketing.payment.infrastructure.config.TossPaymentConfig;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TossPaymentAuthUtils {
    public static String encodeBasicAuth(TossPaymentConfig tossPaymentConfig) {
        return Base64.getEncoder()
                .encodeToString((tossPaymentConfig.getTestSecretApiKey() + ":").getBytes(StandardCharsets.UTF_8));
    }
}
