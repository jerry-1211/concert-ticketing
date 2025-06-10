package com.jerry.ticketing.payment.infrastructure.external;

import com.jerry.ticketing.global.util.AuthUtils;
import com.jerry.ticketing.global.config.payment.TossPaymentConfig;
import com.jerry.ticketing.payment.application.dto.ConfirmPaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentClient {

    private final TossPaymentConfig tossPaymentConfig;

    public ConfirmPaymentDto.Response confirmPayment(ConfirmPaymentDto.Request request) {

        WebClient webClient = WebClient.create();
        String encodeAuth = AuthUtils.encodeBasicAuth(tossPaymentConfig);

        return webClient.post()
                .uri(tossPaymentConfig.getBaseUrl() + "/confirm")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeAuth)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ConfirmPaymentDto.Response.class)
                .block();
    }
}
