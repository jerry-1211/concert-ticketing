package com.jerry.ticketing.global.config.payment;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "toss.payments")
public class TossPaymentConfig {
    private String testClientApiKey;
    private String successUrl;
    private String failUrl;
    private String baseUrl;
}
