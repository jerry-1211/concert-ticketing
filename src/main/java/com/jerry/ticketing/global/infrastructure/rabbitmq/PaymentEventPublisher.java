package com.jerry.ticketing.global.infrastructure.rabbitmq;

import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishConfirmEvent(ConfirmPaymentDto.Request request) {
        rabbitTemplate.convertAndSend("payment.exchange", "payment.confirm", request);
    }


    public void publishWebhookEvent(WebhookPaymentDto.Request.PaymentData data) {
        rabbitTemplate.convertAndSend("payment.exchange", "payment.webhook", data);
    }
}
