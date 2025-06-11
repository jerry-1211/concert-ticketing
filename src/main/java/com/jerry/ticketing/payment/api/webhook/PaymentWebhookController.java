package com.jerry.ticketing.payment.api.webhook;

import com.jerry.ticketing.payment.application.PaymentWebhookService;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentWebhookService paymentWebhookService;

    /**
     * Toss 결제 완료 후 webhook
     */
    @PostMapping("/payment")
    public ResponseEntity<Void> handleWebhook(
            @RequestBody WebhookPaymentDto.Request request) {

        paymentWebhookService.handleWebhook(request);

        return ResponseEntity.ok().build();
    }

}
