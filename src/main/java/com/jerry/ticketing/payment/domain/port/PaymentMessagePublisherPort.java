package com.jerry.ticketing.payment.domain.port;

import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;

public interface PaymentMessagePublisherPort {

    void publishConfirmEvent(ConfirmPaymentDto.Request request);

    void publishWebhookEvent(WebhookPaymentDto.Request.PaymentData data);

}
