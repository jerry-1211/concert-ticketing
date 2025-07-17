package com.jerry.ticketing.fake;

import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/webhook")
@Profile("test")
@RequiredArgsConstructor
public class FakePaymentWebhookController {

    private final FakePaymentWebhookService fakePaymentWebhookService;

    /**
     * Toss 결제 완료 후 토스 서버로 부터 받는 webhook 입니다.
     */
    @PostMapping("/payment")
    public ResponseEntity<Void> handleWebhook(
            @RequestBody WebhookPaymentDto.Request request) {

        fakePaymentWebhookService.handle(request);

        return ResponseEntity.ok().build();
    }

}
