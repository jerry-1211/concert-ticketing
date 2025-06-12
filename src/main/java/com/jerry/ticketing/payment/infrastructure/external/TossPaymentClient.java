package com.jerry.ticketing.payment.infrastructure.external;

import com.jerry.ticketing.payment.util.TossPaymentAuthUtils;
import com.jerry.ticketing.payment.infrastructure.config.TossPaymentConfig;
import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
@RequiredArgsConstructor
public class TossPaymentClient {

    private final TossPaymentConfig tossPaymentConfig;

    public void confirmPayment(ConfirmPaymentDto.Request request) {

        WebClient webClient = WebClient.create();
        String encodeAuth = TossPaymentAuthUtils.encodeBasicAuth(tossPaymentConfig);

        webClient.post()
                .uri(tossPaymentConfig.getBaseUrl() + "/confirm")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeAuth)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ConfirmPaymentDto.Response.class)
                .block();
    }
}
