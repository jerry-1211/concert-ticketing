package com.jerry.ticketing.global.infrastructure.rabbitmq;


import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.payment.domain.port.PaymentEventConsumerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@RabbitListener(queues = "payment.process.queue")
@Slf4j
public class PaymentEventConsumerAdapter {

    private final PaymentEventConsumerPort paymentEventConsumerPort;


    @RabbitHandler
    public void handleConfirmEvent(ConfirmPaymentDto.Request request) {
        paymentEventConsumerPort.handleConfirmEvent(request);
    }


    @RabbitHandler
    public void handleWebhookEvent(WebhookPaymentDto.Request.PaymentData data) {
        paymentEventConsumerPort.handleWebhookEvent(data);
    }

}
