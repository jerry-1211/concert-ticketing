package com.jerry.ticketing.application.payment;

import com.jerry.ticketing.application.payment.util.AuthUtils;
import com.jerry.ticketing.global.config.payment.TossPaymentConfig;
import com.jerry.ticketing.dto.ConfirmTossPayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentClient {

    private final TossPaymentConfig tossPaymentConfig;

    public ConfirmTossPayment.Response confirmPayment(ConfirmTossPayment.Request request) {

        WebClient webClient = WebClient.create();
        String encodeAuth = AuthUtils.encodeBasicAuth(tossPaymentConfig);

        return webClient.post()
                .uri(tossPaymentConfig.getBaseUrl() + "/confirm")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeAuth)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ConfirmTossPayment.Response.class)
                .block();
    }
}
