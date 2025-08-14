package com.jerry.ticketing.global.infrastructure.rabbitmq;

import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.payment.domain.port.PaymentMessagePublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMessagePublisherAdapter implements PaymentMessagePublisherPort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishConfirmEvent(ConfirmPaymentDto.Request request) {
        rabbitTemplate.convertAndSend("payment.exchange", "payment.confirm", request);
    }

    @Override
    public void publishWebhookEvent(WebhookPaymentDto.Request.PaymentData data) {
        rabbitTemplate.convertAndSend("payment.exchange", "payment.webhook", data);
    }
}
