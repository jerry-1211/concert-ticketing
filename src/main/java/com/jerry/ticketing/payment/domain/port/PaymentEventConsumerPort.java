package com.jerry.ticketing.payment.domain.port;

import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;

public interface PaymentEventConsumerPort {

    void handleWebhookEvent(WebhookPaymentDto.Request.PaymentData data);

    void handleConfirmEvent(ConfirmPaymentDto.Request request);

}