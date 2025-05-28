package com.jerry.ticketing.application.payment;

import com.jerry.ticketing.config.payment.TossPaymentConfig;
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

    public ConfirmTossPayment.Response confirmPayment(String paymentKey, String orderId, String amount) {

        WebClient webClient = WebClient.create();

        String encodeAuth = Base64.getEncoder()
                .encodeToString((tossPaymentConfig.getTestClientApiKey() + ":").getBytes(StandardCharsets.UTF_8));

        ConfirmTossPayment.Request request = ConfirmTossPayment.Request.builder()
                .paymentKey(paymentKey)
                .orderId(orderId)
                .amount(amount)
                .build();

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
