package com.jerry.ticketing.loadtest;

import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.infrastructure.config.TossPaymentConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Profile("test")
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class LoadTestPaymentApiController {

    private final TossPaymentConfig tossPaymentConfig;
    private final LoadTestPaymentCommandService loadTestPaymentCommandService;


    @PostMapping("/request")
    public ResponseEntity<CreatePaymentDto.Response> createPayment(@Valid @RequestBody CreatePaymentDto.Request request) {
        CreatePaymentDto.Response response = loadTestPaymentCommandService.createPayment(request);
        response.setPaymentUrls(tossPaymentConfig);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/toss/confirm")
    public ResponseEntity<CreatePaymentDto.Response> tossPaymentSuccess(
            @RequestBody ConfirmPaymentDto.Request request) {

        CreatePaymentDto.Response response = loadTestPaymentCommandService.confirmPayment(request);

        return ResponseEntity.ok(response);
    }

}
