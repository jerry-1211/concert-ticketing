package com.jerry.ticketing.fake;

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
public class FakePaymentApiController {

    private final TossPaymentConfig tossPaymentConfig;
    private final FakePaymentCommandService fakePaymentCommandService;


    @PostMapping("/request")
    public ResponseEntity<CreatePaymentDto.Response> createPayment(@Valid @RequestBody CreatePaymentDto.Request request) {
        CreatePaymentDto.Response response = fakePaymentCommandService.createPayment(request);
        response.setPaymentUrls(tossPaymentConfig);
        response.setProfile("test");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/toss/confirm")
    public ResponseEntity<CreatePaymentDto.Response> tossPaymentSuccess(
            @RequestBody ConfirmPaymentDto.Request request) {

        CreatePaymentDto.Response response = fakePaymentCommandService.confirmPayment(request);

        return ResponseEntity.ok(response);
    }

}
